package com.irlix.traineeship.workplacebooking.mappers;

import com.irlix.traineeship.workplacebooking.dto.WorkspaceForAdminDTO;
import com.irlix.traineeship.workplacebooking.dto.WorkspaceForUserDTO;
import com.irlix.traineeship.workplacebooking.entities.WorkspaceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WorkspaceMapper {
    WorkspaceForAdminDTO toWorkspaceForAdminDTO(WorkspaceEntity workspaceEntity);

    WorkspaceEntity toWorkspaceEntity(WorkspaceForAdminDTO workspaceForAdminDTO);

    void updateWorkspaceEntityFromWorkspaceForAdminDTO(WorkspaceForAdminDTO workspaceForAdminDTO,
                                                       @MappingTarget WorkspaceEntity toWorkspaceEntity);

    WorkspaceForUserDTO toWorkspaceForUserDTO(WorkspaceEntity workspaceEntity);

}
