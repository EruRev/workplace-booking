package com.irlix.traineeship.workplacebooking.mappers;

import com.irlix.traineeship.workplacebooking.dto.WorkplaceForAdminDTO;
import com.irlix.traineeship.workplacebooking.dto.WorkplaceForUserDTO;
import com.irlix.traineeship.workplacebooking.entities.WorkplaceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WorkplaceMapper {
    WorkplaceForAdminDTO toWorkplaceForAdminDTO(WorkplaceEntity workplaceEntity);

    WorkplaceEntity toWorkplaceEntity(WorkplaceForAdminDTO workplaceDTO);

    void updateWorkplaceEntityFromWorkplaceForAdminDTO(WorkplaceForAdminDTO workplaceForAdminDTO,
                                                       @MappingTarget WorkplaceEntity toWorkspaceEntity);


    WorkplaceForUserDTO toWorkplaceForUserDTO(WorkplaceEntity workplaceEntity);

}
