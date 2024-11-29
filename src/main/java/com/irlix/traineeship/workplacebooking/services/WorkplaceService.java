package com.irlix.traineeship.workplacebooking.services;

import com.irlix.traineeship.workplacebooking.dto.BookingForUserDTO;
import com.irlix.traineeship.workplacebooking.dto.WorkplaceDTO;
import com.irlix.traineeship.workplacebooking.dto.WorkplaceForAdminDTO;
import com.irlix.traineeship.workplacebooking.dto.WorkplaceForUserDTO;
import com.irlix.traineeship.workplacebooking.entities.WorkplaceEntity;
import com.irlix.traineeship.workplacebooking.entities.enums.CreateWayEnum;
import com.irlix.traineeship.workplacebooking.exceptions.BlankFieldsException;
import com.irlix.traineeship.workplacebooking.exceptions.NotUniqueNumberException;
import com.irlix.traineeship.workplacebooking.exceptions.ResourceNotFoundException;
import com.irlix.traineeship.workplacebooking.mappers.WorkplaceMapper;
import com.irlix.traineeship.workplacebooking.repositories.WorkplaceRepository;
import com.irlix.traineeship.workplacebooking.services.impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkplaceService {
    private final WorkplaceRepository workplaceRepository;
    private final WorkplaceMapper workplaceMapper;
    private final UserService userService;

    public WorkplaceForAdminDTO createWorkplace(final WorkplaceForAdminDTO workplaceForAdminDTO) {
        if (Objects.isNull(workplaceForAdminDTO.number()) || Objects.isNull(workplaceForAdminDTO.workspaceId())) {
            throw new BlankFieldsException("Workplace number and workspace id is required");
        }
        if (workplaceRepository.existsByWorkspaceIdAndNumber(workplaceForAdminDTO.workspaceId(),
                workplaceForAdminDTO.number())) {
            throw new NotUniqueNumberException("A workplace with this number already exists in this workspace.");
        }
        WorkplaceEntity workplaceEntity = workplaceMapper.toWorkplaceEntity(workplaceForAdminDTO);
        workplaceEntity.setIsDeleted(false);
        return workplaceMapper.toWorkplaceForAdminDTO(workplaceRepository.save(workplaceEntity));
    }

    public WorkplaceDTO showWorkplace(final UserDetailsImpl currentUser, final UUID id) {
        WorkplaceEntity workplaceEntity = workplaceRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Workplace not found"));
        return userService.checkAdminRole(currentUser.getId())
                ? workplaceMapper.toWorkplaceForAdminDTO(workplaceEntity)
                : workplaceMapper.toWorkplaceForUserDTO(workplaceEntity);
    }

    public List<WorkplaceDTO> showAllWorkplaces(final UserDetailsImpl currentUser, final UUID workspaceId,
                                                        final Boolean status) {
        List<WorkplaceEntity> workplaces = (Objects.isNull(status)) ?
                workplaceRepository.findByWorkspaceId(workspaceId) :
                workplaceRepository.findByWorkspaceIdAndIsDeleted(workspaceId, status);
        return workplaces.stream().map(workplaceEntity ->
                mapToWorkplaceDTO(workplaceEntity, checkCurrenUserRole(currentUser))).toList();
    }

    public WorkplaceEntity getById(final UUID id) {
        return workplaceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Workplace not found"));
    }

    public List<WorkplaceDTO> searchWorkplaces(final UserDetailsImpl currentUser, final Integer number) {
        List<WorkplaceEntity> workplaces = (Objects.isNull(number)) ? workplaceRepository.findAll() :
                workplaceRepository.findByNumber(number);
        return workplaces.stream().map(workplaceEntity ->
                        mapToWorkplaceDTO(workplaceEntity, checkCurrenUserRole(currentUser))).toList();
    }

    public WorkplaceForAdminDTO updateWorkplace(final UUID id, final WorkplaceForAdminDTO workplaceForAdminDTO) {
        WorkplaceEntity workplaceEntity = workplaceRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Workplace not found"));
        workplaceMapper.updateWorkplaceEntityFromWorkplaceForAdminDTO(workplaceForAdminDTO, workplaceEntity);
        workplaceEntity.setIsDeleted(false);
        return workplaceMapper.toWorkplaceForAdminDTO(workplaceRepository.save(workplaceEntity));
    }

    @Transactional
    public void deleteWorkplace(final UUID id) {
        WorkplaceEntity workplace = workplaceRepository.getWorkplace(id);
        if(Objects.nonNull(workplace)) {
            workplace.setIsDeleted(true);
            LocalDateTime now = LocalDateTime.now();
            workplace.getBookings().forEach(bookingEntity -> {
                bookingEntity.setConfirmed(false);
                bookingEntity.setCancellationDate(now);
                bookingEntity.setCancellationComment("Workplace was deleted");
                //ToDo: отменяются все заявки о поломках
            });
            workplaceRepository.save(workplace);
        }
    }

    @Deprecated
    public Optional<WorkplaceEntity> searchWorkplaceById(UUID id) {
        return workplaceRepository.findById(id);
    }

    @Deprecated
    public List<WorkplaceForUserDTO> searchWorkplaceByNumber(Integer number){
        if(Objects.isNull(number)) {
        return workplaceRepository.findAll().stream().map(workplaceMapper::toWorkplaceForUserDTO).toList();
        }
        return workplaceRepository.findByNumber(number).stream().map(workplaceMapper::toWorkplaceForUserDTO).toList();
    }

    public List<WorkplaceDTO> getWorkplacesForNewBooking(final UserDetailsImpl currentUser,
                                                                 final CreateWayEnum createWay,
                                                                 final BookingForUserDTO bookingForUserDTO) {
        List<WorkplaceEntity> workplaces = new ArrayList<>();
        switch (createWay) {
            case WORKPLACE:
                workplaces = workplaceRepository.findAll();
                break;
            case DATE:
                workplaces = workplaceRepository.findByDate(bookingForUserDTO.bookingStart(),
                        bookingForUserDTO.bookingEnd());
                break;
        };
        return workplaces.stream().map(workplaceEntity ->
                mapToWorkplaceDTO(workplaceEntity, checkCurrenUserRole(currentUser))).toList();
    }

    private boolean checkCurrenUserRole(final UserDetailsImpl currentUser) {
        return userService.checkAdminRole(currentUser.getId());
    }

    private WorkplaceDTO mapToWorkplaceDTO(final WorkplaceEntity workplaceEntity, final boolean isAdmin) {
        return isAdmin ? workplaceMapper.toWorkplaceForAdminDTO(workplaceEntity) :
                workplaceMapper.toWorkplaceForUserDTO(workplaceEntity);
    }
}
