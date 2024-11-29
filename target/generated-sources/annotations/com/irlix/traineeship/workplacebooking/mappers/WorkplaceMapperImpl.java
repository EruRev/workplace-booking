package com.irlix.traineeship.workplacebooking.mappers;

import com.irlix.traineeship.workplacebooking.dto.WorkplaceForAdminDTO;
import com.irlix.traineeship.workplacebooking.dto.WorkplaceForUserDTO;
import com.irlix.traineeship.workplacebooking.entities.WorkplaceEntity;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-03T10:56:05+0300",
    comments = "version: 1.6.0, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class WorkplaceMapperImpl implements WorkplaceMapper {

    @Override
    public WorkplaceForAdminDTO toWorkplaceForAdminDTO(WorkplaceEntity workplaceEntity) {
        if ( workplaceEntity == null ) {
            return null;
        }

        Integer number = null;
        String description = null;
        UUID workspaceId = null;

        number = workplaceEntity.getNumber();
        description = workplaceEntity.getDescription();
        workspaceId = workplaceEntity.getWorkspaceId();

        boolean isDeleted = false;

        WorkplaceForAdminDTO workplaceForAdminDTO = new WorkplaceForAdminDTO( number, description, workspaceId, isDeleted );

        return workplaceForAdminDTO;
    }

    @Override
    public WorkplaceEntity toWorkplaceEntity(WorkplaceForAdminDTO workplaceDTO) {
        if ( workplaceDTO == null ) {
            return null;
        }

        WorkplaceEntity workplaceEntity = new WorkplaceEntity();

        if ( workplaceDTO.number() != null ) {
            workplaceEntity.setNumber( workplaceDTO.number() );
        }
        workplaceEntity.setDescription( workplaceDTO.description() );
        workplaceEntity.setWorkspaceId( workplaceDTO.workspaceId() );

        return workplaceEntity;
    }

    @Override
    public void updateWorkplaceEntityFromWorkplaceForAdminDTO(WorkplaceForAdminDTO workplaceForAdminDTO, WorkplaceEntity toWorkspaceEntity) {
        if ( workplaceForAdminDTO == null ) {
            return;
        }

        if ( workplaceForAdminDTO.number() != null ) {
            toWorkspaceEntity.setNumber( workplaceForAdminDTO.number() );
        }
        toWorkspaceEntity.setDescription( workplaceForAdminDTO.description() );
        toWorkspaceEntity.setWorkspaceId( workplaceForAdminDTO.workspaceId() );
    }

    @Override
    public WorkplaceForUserDTO toWorkplaceForUserDTO(WorkplaceEntity workplaceEntity) {
        if ( workplaceEntity == null ) {
            return null;
        }

        Integer number = null;
        String description = null;
        UUID workspaceId = null;

        number = workplaceEntity.getNumber();
        description = workplaceEntity.getDescription();
        workspaceId = workplaceEntity.getWorkspaceId();

        WorkplaceForUserDTO workplaceForUserDTO = new WorkplaceForUserDTO( number, description, workspaceId );

        return workplaceForUserDTO;
    }
}
