package com.irlix.traineeship.workplacebooking.services;

import com.irlix.traineeship.workplacebooking.dto.WorkspaceDTO;
import com.irlix.traineeship.workplacebooking.dto.WorkspaceForAdminDTO;
import com.irlix.traineeship.workplacebooking.dto.WorkspaceForUserDTO;
import com.irlix.traineeship.workplacebooking.entities.WorkspaceEntity;
import com.irlix.traineeship.workplacebooking.exceptions.BlankFieldsException;
import com.irlix.traineeship.workplacebooking.exceptions.ResourceNotFoundException;
import com.irlix.traineeship.workplacebooking.mappers.WorkspaceMapper;
import com.irlix.traineeship.workplacebooking.repositories.WorkspaceRepository;
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
public class WorkspaceService {
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMapper workspaceMapper;
    private final UserService userService;

    public WorkspaceForAdminDTO createWorkspace(final WorkspaceForAdminDTO workspaceForAdminDTO) {
        if (Objects.isNull(workspaceForAdminDTO.name()) || Objects.isNull(workspaceForAdminDTO.floorNumber())
                || Objects.isNull(workspaceForAdminDTO.officeId())) {
            throw new BlankFieldsException("Workspace name, floor number and office id is required");
        }
        WorkspaceEntity workspaceEntity = workspaceMapper.toWorkspaceEntity(workspaceForAdminDTO);
        workspaceEntity.setIsDeleted(false);
        return workspaceMapper.toWorkspaceForAdminDTO(workspaceRepository.save(workspaceEntity));
    }

    public WorkspaceDTO showWorkspace(final UserDetailsImpl currentUser, final UUID id) {
        WorkspaceEntity workspaceEntity = workspaceRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Workspace not found"));
        return userService.checkAdminRole(currentUser.getId()) ?
                workspaceMapper.toWorkspaceForAdminDTO(workspaceEntity) :
                workspaceMapper.toWorkspaceForUserDTO(workspaceEntity);
    }

    public List<WorkspaceDTO> showAllWorkspaces(final UserDetailsImpl currentUser, final UUID officeId,
                                                final Boolean status) {
        List<WorkspaceEntity> workspaces = (Objects.isNull(status)) ? workspaceRepository.findByOfficeId(officeId) :
                workspaceRepository.findByOfficeIdAndIsDeleted(officeId, status);
        return workspaces.stream().map(workspaceEntity ->
                mapToWorkspaceDTO(workspaceEntity, checkCurrenUserRole(currentUser))).toList();
    }

    public Optional<WorkspaceEntity> getById(final UUID id) {
        return workspaceRepository.findById(id);
    }

    public List<WorkspaceDTO> searchWorkspaces(final UserDetailsImpl currentUser, final String name,
                                                       final Short floorNumber, final Short roomNumber) {
        List<WorkspaceEntity> workspaces = (Objects.isNull(name) & Objects.isNull(floorNumber)
                & Objects.isNull(roomNumber)) ? workspaceRepository.findAll() :
                workspaceRepository.findByNameOrFloorNumberOrRoomNumber(name, floorNumber, roomNumber);
        return workspaces.stream().map(workspaceEntity ->
                        mapToWorkspaceDTO(workspaceEntity, checkCurrenUserRole(currentUser))).toList();
    }

    public WorkspaceForAdminDTO updateWorkspace(final UUID id, final WorkspaceForAdminDTO workspaceForAdminDTO) {
        WorkspaceEntity workspaceEntity = workspaceRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Workspace not found"));
        workspaceMapper.updateWorkspaceEntityFromWorkspaceForAdminDTO(workspaceForAdminDTO, workspaceEntity);
        workspaceEntity.setIsDeleted(false);
        return workspaceMapper.toWorkspaceForAdminDTO(workspaceRepository.save(workspaceEntity));
    }

    @Transactional
    public void deleteWorkspace(final UUID workspaceId) {
        WorkspaceEntity workspace = workspaceRepository.getWorkspace(workspaceId);
        if (Objects.nonNull(workspace)) {
            workspace.setIsDeleted(true);
            workspace.getWorkplaces().forEach(workplace -> {
                workplace.setIsDeleted(true);
                workplace.getBookings().forEach(booking -> {
                    booking.setConfirmed(false);
                    booking.setCancellationDate(LocalDateTime.now());
                    booking.setCancellationComment("Workspace was deleted");
                });
            });
            //ToDo: отменяются все заявки о поломках
            workspaceRepository.save(workspace);
        }
    }

    private boolean checkCurrenUserRole(final UserDetailsImpl currentUser) {
        return userService.checkAdminRole(currentUser.getId());
    }

    private WorkspaceDTO mapToWorkspaceDTO(final WorkspaceEntity workspaceEntity, final boolean isAdmin) {
        return isAdmin ? workspaceMapper.toWorkspaceForAdminDTO(workspaceEntity) :
                workspaceMapper.toWorkspaceForUserDTO(workspaceEntity);
    }

    @Deprecated
    public List<WorkspaceForUserDTO> searchWorkspacesByName(final String name) {
        if (Objects.isNull(name)) {
            return workspaceRepository.findAll().stream().map(workspaceMapper::toWorkspaceForUserDTO).toList();
        }
        return workspaceRepository.findByName(name).stream().map(workspaceMapper::toWorkspaceForUserDTO).toList();
    }

    @Deprecated
    public List<WorkspaceForUserDTO> searchWorkspacesByFloor(final Short floorNumber) {
        if (Objects.isNull(floorNumber)) {
            return workspaceRepository.findAll().stream().map(workspaceMapper::toWorkspaceForUserDTO).toList();
        }
        return workspaceRepository.findByFloorNumber(floorNumber)
                .stream().map(workspaceMapper::toWorkspaceForUserDTO).toList();
    }

    @Deprecated
    public List<WorkspaceForUserDTO> searchWorkspacesByRoom(final Short roomNumber) {
        if (Objects.isNull(roomNumber)) {
            return workspaceRepository.findAll().stream().map(workspaceMapper::toWorkspaceForUserDTO).toList();
        }
        return workspaceRepository.findByRoomNumber(roomNumber)
                .stream().map(workspaceMapper::toWorkspaceForUserDTO).toList();
    }
}
