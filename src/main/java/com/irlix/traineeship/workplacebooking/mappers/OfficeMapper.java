package com.irlix.traineeship.workplacebooking.mappers;

import com.irlix.traineeship.workplacebooking.dto.OfficeForAdminDTO;
import com.irlix.traineeship.workplacebooking.dto.OfficeForOfficeDTO;
import com.irlix.traineeship.workplacebooking.entities.OfficeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface OfficeMapper {
    @Mapping(target = "isDeleted", source = "isDeleted")
    OfficeForAdminDTO toOfficeForAdminDTO(OfficeEntity officeEntity);

    OfficeEntity toOfficeEntity(OfficeForAdminDTO officeForAdminDTO);

    @Mapping(target = "id", ignore = true)
    void updateOfficeEntityFromOfficeForAdminDTO(OfficeForAdminDTO officeForAdminDTO,
                                                 @MappingTarget OfficeEntity toOfficeEntity);

    @Mapping(target = "officeAddress", source = "address")
    @Mapping(target = "officeName", source = "name")
    OfficeForOfficeDTO toOfficeForOfficeDTO(OfficeEntity officeEntity);
}
