package com.irlix.traineeship.workplacebooking.services;

import com.irlix.traineeship.workplacebooking.dto.BookingDTO;
import com.irlix.traineeship.workplacebooking.dto.BookingForAdminDTO;
import com.irlix.traineeship.workplacebooking.dto.BookingForUserDTO;
import com.irlix.traineeship.workplacebooking.entities.BookingEntity;
import com.irlix.traineeship.workplacebooking.entities.OfficeEntity;
import com.irlix.traineeship.workplacebooking.entities.UserEntity;
import com.irlix.traineeship.workplacebooking.entities.WorkplaceEntity;
import com.irlix.traineeship.workplacebooking.entities.WorkspaceEntity;
import com.irlix.traineeship.workplacebooking.entities.enums.StatusEnum;
import com.irlix.traineeship.workplacebooking.exceptions.ResourceNotFoundException;
import com.irlix.traineeship.workplacebooking.exceptions.TimeOutForConfirmException;
import com.irlix.traineeship.workplacebooking.mappers.BookingMapper;
import com.irlix.traineeship.workplacebooking.repositories.BookingRepository;
import com.irlix.traineeship.workplacebooking.repositories.OfficeRepository;
import com.irlix.traineeship.workplacebooking.services.impl.UserDetailsImpl;
import com.irlix.traineeship.workplacebooking.validatorbooking.BookingValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final WorkplaceService workplaceService;
    private final WorkspaceService workspaceService;
    private final OfficeService officeService;
    private final UserService userService;

    private final int MINUTES_FOR_CONFIRMATION = 15;

    @Autowired
    @Setter
    private List<BookingValidator> bookingValidators;
    @Autowired
    private OfficeRepository officeRepository;

    @Transactional
    public BookingDTO createBooking(final UserDetailsImpl currentUser,
                                           final UUID workplaceId,
                                           final BookingForAdminDTO bookingForAdminDTO) {
        UserEntity userEntity = userService.getUserById(currentUser.getId());
        boolean isAdmin = userService.checkAdminRole(currentUser.getId());
        BookingEntity bookingEntity = bookingMapper.toBookingEntity(bookingForAdminDTO);
        bookingEntity.setWorkplaceId(workplaceId);
        bookingEntity.setBookingDate(LocalDateTime.now());
        if (!isAdmin || Objects.isNull(bookingForAdminDTO.user())) {
            bookingEntity.setUserId(currentUser.getId());
        }
        else {
            UserEntity targetUser = userService.getUserByName(bookingForAdminDTO.user());
            bookingEntity.setUserId(targetUser.getId());
        }
        int bookingMinutes = Math.toIntExact(
                Duration.between(bookingEntity.getBookingStart(), bookingEntity.getBookingEnd()).toMinutes()
        );
        for (BookingValidator bookingValidator : bookingValidators) {
            if (!bookingValidator.check(bookingEntity)) {
                return null;
            }
        }
        userEntity.setAvailableMinutes(userEntity.getAvailableMinutes() - bookingMinutes);
        userService.update(userEntity);
        if (isAdmin) {
            return convertToBookingForAdminDTO(bookingRepository.save(bookingEntity));
        }
        return convertToBookingForUserDTO(bookingRepository.save(bookingEntity));
    }

    public BookingDTO showBooking(final UserDetailsImpl currentUser, final UUID id) {
        BookingEntity bookingEntity = bookingRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Booking not found"));
        if (userService.checkAdminRole(currentUser.getId())) {
            return convertToBookingForAdminDTO(bookingEntity);
        }
        return convertToBookingForUserDTO(bookingEntity);
    }

    public List<BookingDTO> showAllBookings(final UserDetailsImpl currentUser, final StatusEnum status) {
        List<BookingDTO> bookingDTOs = new ArrayList<>();
        List<BookingEntity> bookingEntities;
        switch (status) {
            case null:
                bookingEntities = bookingRepository.findAll();
                break;
            case ACTIVE:
                bookingEntities = bookingRepository.getByCancellationDateNull();
                break;
            case DELETED:
                bookingEntities = bookingRepository.getByCancellationDateNotNull();
                break;
        }
        if (userService.checkAdminRole(currentUser.getId())) {
            for (BookingEntity bookingEntity : bookingEntities) {
                bookingDTOs.add(convertToBookingForAdminDTO(bookingEntity));
            }
        }
        else {
            for (BookingEntity bookingEntity : bookingEntities) {
                bookingDTOs.add(convertToBookingForUserDTO(bookingEntity));
            }
        }
        return bookingDTOs;
    }

    public List<BookingForAdminDTO> searchBookings(final String officeName, final String workspaceName,
                                                   final Integer workplaceNumber, final String username,
                                                   final LocalDate bookingDate) {
        List<BookingForAdminDTO> bookingsDTO = new ArrayList<>();
        List<BookingEntity> bookingsEntity = bookingRepository
                .findBookings(officeName, workspaceName, workplaceNumber, username, bookingDate);
        for(BookingEntity bookingEntity : bookingsEntity) {
            bookingsDTO.add(convertToBookingForAdminDTO(bookingEntity));
        }
        return bookingsDTO;
    }

    public BookingDTO showCurrentBooking(final UserDetailsImpl currentUser) {
        BookingEntity bookingEntity = bookingRepository.findCurrentBooking(currentUser.getId(), LocalDateTime.now());
        if (Objects.isNull(bookingEntity) || !bookingEntity.isConfirmed()) {
            return null;
        }
        if (userService.checkAdminRole(currentUser.getId())) {
            return convertToBookingForAdminDTO(bookingEntity);
        }
        return convertToBookingForUserDTO(bookingEntity);
    }

    public List<BookingDTO> showBookingsByConfirmed(final UserDetailsImpl currentUser, final boolean isConfirmed) {
        List<BookingEntity> bookings = bookingRepository
                .findByUserIdAndIsConfirmed(currentUser.getId(), isConfirmed);
        List<BookingDTO> bookingsDTO = new ArrayList<>();
        if (userService.checkAdminRole(currentUser.getId())) {
            for(BookingEntity bookingEntity : bookings) {
                bookingsDTO.add(convertToBookingForAdminDTO(bookingEntity));
            }
        }
        else {
            for(BookingEntity bookingEntity : bookings) {
                bookingsDTO.add(convertToBookingForUserDTO(bookingEntity));
            }
        }
        return bookingsDTO;
    }

    public void cancelBooking(final UserDetailsImpl currentUser, final UUID id, final String reason) {
        BookingEntity bookingEntity = bookingRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Booking not found"));
        if (bookingEntity.getUserId().equals(currentUser.getId()) || userService.checkAdminRole(currentUser.getId())) {
            bookingEntity.setConfirmed(false);
            bookingEntity.setCancellationDate(LocalDateTime.now());
            bookingEntity.setCancellationComment(reason);
            bookingRepository.save(bookingEntity);
        }
    }

    public void confirmBooking(final UserDetailsImpl currentUser, final UUID id) {
        BookingEntity bookingEntity = bookingRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Booking not found"));
        if (bookingEntity.getUserId().equals(currentUser.getId())) {
            if(LocalDateTime.now().isAfter(bookingEntity.getBookingEnd())) {
                throw new TimeOutForConfirmException("It is not possible to confirm a past booking");
            }
            bookingEntity.setConfirmed(true);
            bookingRepository.save(bookingEntity);
        }
    }

    @Transactional
    public void completeBooking() {
        LocalDateTime now = LocalDateTime.now();
        List<BookingEntity> bookingEntities =
                bookingRepository.findByIsConfirmedAndCancellationDate(true, null);
        for(BookingEntity bookingEntity : bookingEntities) {
            if (Duration.between(bookingEntity.getBookingEnd(), now).toMinutes() >= 0) {
                bookingEntity.setCancellationDate(now);
                bookingEntity.setCancellationComment("The booking has expired");
                bookingRepository.save(bookingEntity);
                changeAvailableMinutesForUser(bookingEntity);
            }
        }
    }

    @Transactional
    public void autoCancelBooking() {
        List<BookingEntity> bookingEntities =
                bookingRepository.findByIsConfirmedAndCancellationDate(false, null);
        LocalDateTime now = LocalDateTime.now();
        for(BookingEntity bookingEntity : bookingEntities) {
            if (Duration.between(bookingEntity.getBookingDate(), now).toMinutes() > MINUTES_FOR_CONFIRMATION) {
                bookingEntity.setCancellationDate(now);
                bookingEntity.setCancellationComment("The time for confirmation has expired");
                bookingRepository.save(bookingEntity);
                changeAvailableMinutesForUser(bookingEntity);
            }
        }
    }

    private WorkplaceEntity getWorkplaceByBooking(final BookingEntity bookingEntity) {
        return workplaceService.getById(bookingEntity.getWorkplaceId());
    }

    private WorkspaceEntity getWorkspaceByBooking(final BookingEntity bookingEntity) {
        return workspaceService.getById(getWorkplaceByBooking(bookingEntity).getWorkspaceId()).orElseThrow(()
                -> new ResourceNotFoundException("Workspace not found"));
    }

    private OfficeEntity getOfficeByBooking(final BookingEntity bookingEntity) {
        return officeService.getById(getWorkspaceByBooking(bookingEntity).getOfficeId()).orElseThrow(() ->
                new ResourceNotFoundException("Office not found"));
    }

    private UserEntity getUserByBooking(final BookingEntity bookingEntity) {
        return userService.getUserById(bookingEntity.getUserId());
    }

    private BookingForUserDTO convertToBookingForUserDTO(final BookingEntity bookingEntity) {
        return bookingMapper.toBookingForUserDTO(bookingEntity, getOfficeByBooking(bookingEntity),
                getWorkspaceByBooking(bookingEntity), getWorkplaceByBooking(bookingEntity));
    }

    private BookingForAdminDTO convertToBookingForAdminDTO(final BookingEntity bookingEntity) {
        return bookingMapper.toBookingForAdminDTO(bookingEntity, getOfficeByBooking(bookingEntity),
                getWorkspaceByBooking(bookingEntity), getWorkplaceByBooking(bookingEntity),
                getUserByBooking(bookingEntity));
    }

    private void changeAvailableMinutesForUser(final BookingEntity bookingEntity) {
        UserEntity userEntity = userService.getUserById(bookingEntity.getUserId());
        userEntity.setAvailableMinutes(userEntity.getAvailableMinutes() + Math.toIntExact(
                Duration.between(bookingEntity.getBookingStart(), bookingEntity.getBookingEnd()).toMinutes())
        );
        userService.update(userEntity);
    }
}