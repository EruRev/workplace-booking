package com.irlix.traineeship.workplacebooking.mappers;

import com.irlix.traineeship.workplacebooking.dto.WorkspaceForAdminDTO;
import com.irlix.traineeship.workplacebooking.dto.WorkspaceForUserDTO;
import com.irlix.traineeship.workplacebooking.entities.WorkspaceEntity;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-03T10:56:05+0300",
    comments = "version: 1.6.0, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class WorkspaceMapperImpl implements WorkspaceMapper {

    @Override
    public WorkspaceForAdminDTO toWorkspaceForAdminDTO(WorkspaceEntity workspaceEntity) {
        if ( workspaceEntity == null ) {
            return null;
        }

        String name = null;
        Short floorNumber = null;
        Short roomNumber = null;
        UUID officeId = null;

        name = workspaceEntity.getName();
        floorNumber = workspaceEntity.getFloorNumber();
        roomNumber = workspaceEntity.getRoomNumber();
        officeId = workspaceEntity.getOfficeId();

        boolean isDeleted = false;

        WorkspaceForAdminDTO workspaceForAdminDTO = new WorkspaceForAdminDTO( name, floorNumber, roomNumber, isDeleted, officeId );

        return workspaceForAdminDTO;
    }

    @Override
    public WorkspaceEntity toWorkspaceEntity(WorkspaceForAdminDTO workspaceForAdminDTO) {
        if ( workspaceForAdminDTO == null ) {
            return null;
        }

        WorkspaceEntity workspaceEntity = new WorkspaceEntity();

        workspaceEntity.setName( workspaceForAdminDTO.name() );
        if ( workspaceForAdminDTO.floorNumber() != null ) {
            workspaceEntity.setFloorNumber( workspaceForAdminDTO.floorNumber() );
        }
        if ( workspaceForAdminDTO.roomNumber() != null ) {
            workspaceEntity.setRoomNumber( workspaceForAdminDTO.roomNumber() );
        }
        workspaceEntity.setOfficeId( workspaceForAdminDTO.officeId() );

        return workspaceEntity;
    }

    @Override
    public void updateWorkspaceEntityFromWorkspaceForAdminDTO(WorkspaceForAdminDTO workspaceForAdminDTO, WorkspaceEntity toWorkspaceEntity) {
        if ( workspaceForAdminDTO == null ) {
            return;
        }

        toWorkspaceEntity.setName( workspaceForAdminDTO.name() );
        if ( workspaceForAdminDTO.floorNumber() != null ) {
            toWorkspaceEntity.setFloorNumber( workspaceForAdminDTO.floorNumber() );
        }
        if ( workspaceForAdminDTO.roomNumber() != null ) {
            toWorkspaceEntity.setRoomNumber( workspaceForAdminDTO.roomNumber() );
        }
        toWorkspaceEntity.setOfficeId( workspaceForAdminDTO.officeId() );
    }

    @Override
    public WorkspaceForUserDTO toWorkspaceForUserDTO(WorkspaceEntity workspaceEntity) {
        if ( workspaceEntity == null ) {
            return null;
        }

        String name = null;
        Short floorNumber = null;
        Short roomNumber = null;
        UUID officeId = null;

        name = workspaceEntity.getName();
        floorNumber = workspaceEntity.getFloorNumber();
        roomNumber = workspaceEntity.getRoomNumber();
        officeId = workspaceEntity.getOfficeId();

        WorkspaceForUserDTO workspaceForUserDTO = new WorkspaceForUserDTO( name, floorNumber, roomNumber, officeId );

        return workspaceForUserDTO;
    }
}
