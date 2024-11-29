package com.irlix.traineeship.workplacebooking.services;

import com.irlix.traineeship.workplacebooking.dto.OfficeDTO;
import com.irlix.traineeship.workplacebooking.dto.OfficeForAdminDTO;
import com.irlix.traineeship.workplacebooking.dto.OfficeForOfficeDTO;
import com.irlix.traineeship.workplacebooking.entities.OfficeEntity;
import com.irlix.traineeship.workplacebooking.exceptions.BlankFieldsException;
import com.irlix.traineeship.workplacebooking.exceptions.ResourceNotFoundException;
import com.irlix.traineeship.workplacebooking.mappers.OfficeMapper;
import com.irlix.traineeship.workplacebooking.repositories.OfficeRepository;
import com.irlix.traineeship.workplacebooking.services.impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OfficeService {
    private final OfficeRepository officeRepository;
    private final OfficeMapper officeMapper;
    private final UserService userService;

    public OfficeForAdminDTO createOffice(final OfficeForAdminDTO officeForAdminDTO) {
        if (Objects.isNull(officeForAdminDTO.name()) || Objects.isNull(officeForAdminDTO.address())) {
            throw new BlankFieldsException("Office name and address are required");
        }
        OfficeEntity officeEntity = officeMapper.toOfficeEntity(officeForAdminDTO);
        officeEntity.setIsDeleted(false);
        return officeMapper.toOfficeForAdminDTO(officeRepository.save(officeEntity));
    }

    public OfficeDTO showOffice(final UserDetailsImpl currentUser, final UUID id) {
        OfficeEntity officeEntity = officeRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Office not found"));
        if (userService.checkAdminRole(currentUser.getId())) {
            return officeMapper.toOfficeForAdminDTO(officeEntity);
        }
        return officeMapper.toOfficeForOfficeDTO(officeEntity);
    }

    public List<OfficeDTO> showAllOffices(final UserDetailsImpl currentUser, final Boolean status) {
        List<OfficeEntity> offices = (Objects.isNull(status)) ? officeRepository.findAll() :
                officeRepository.findByIsDeleted(status);
        return offices.stream().map(officeEntity ->
                mapToOfficeDTO(officeEntity, checkCurrentUserRole(currentUser))).toList();
    }

    public Optional<OfficeEntity> getById(final UUID id) {
        return officeRepository.findById(id);
    }

    public List<OfficeDTO> searchOffice(final UserDetailsImpl currentUser, final String name) {
        List<OfficeEntity> offices = (Objects.isNull(name)) ? officeRepository.findAll() :
                officeRepository.findByName(name);
        return offices.stream().map(officeEntity ->
                mapToOfficeDTO(officeEntity, checkCurrentUserRole(currentUser))).toList();
    }

    public OfficeForAdminDTO updateOffice(final UUID id, final OfficeForAdminDTO officeForAdminDTO) {
        OfficeEntity officeEntity = officeRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Office not found"));
        officeMapper.updateOfficeEntityFromOfficeForAdminDTO(officeForAdminDTO, officeEntity);
        officeEntity.setIsDeleted(false);
        return officeMapper.toOfficeForAdminDTO(officeRepository.save(officeEntity));
    }

    @Transactional
    public void deleteOffice(final UUID id) {
        OfficeEntity office = officeRepository.getOffice(id);
        if (Objects.nonNull(office)) {
            office.setIsDeleted(true);
            office.getWorkspaceEntities().forEach(workspaceEntity -> {
                workspaceEntity.setIsDeleted(true);
                workspaceEntity.getWorkplaces().forEach(workplaceEntity -> {
                    workplaceEntity.setIsDeleted(true);
                    workplaceEntity.getBookings().forEach(bookingEntity -> {
                        bookingEntity.setConfirmed(false);
                        bookingEntity.setCancellationDate(LocalDateTime.now());
                        bookingEntity.setCancellationComment("Office was deleted");
                        //ToDo: отменяются все заявки о поломках
                    });
                });
            });
            officeRepository.save(office);
        }
    }

    private boolean checkCurrentUserRole(final UserDetailsImpl currentUser) {
        return userService.checkAdminRole(currentUser.getId());
    }

    private OfficeDTO mapToOfficeDTO(final OfficeEntity officeEntity, final boolean isAdmin) {
        return isAdmin ? officeMapper.toOfficeForAdminDTO(officeEntity) :
                officeMapper.toOfficeForOfficeDTO(officeEntity);
    }

    @Deprecated
    public OfficeForOfficeDTO showOfficeById(final UUID office_Id) {
        OfficeEntity officeEntity = officeRepository.findById(office_Id).orElseThrow(() -> new ResourceNotFoundException("Office not found"));
        return officeMapper.toOfficeForOfficeDTO(officeEntity);
    }

    @Deprecated
    public OfficeForOfficeDTO showOfficeByName(final String office_name) {
        List<OfficeEntity> officeEntity = officeRepository.findByName(office_name);
        if (officeEntity.isEmpty()) {
            throw new ResourceNotFoundException("Office not found");
        }
        return officeMapper.toOfficeForOfficeDTO((OfficeEntity) officeEntity);
    }
}
