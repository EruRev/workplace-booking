package com.irlix.traineeship.workplacebooking.mappers;

import com.irlix.traineeship.workplacebooking.dto.UserForAdminDTO;
import com.irlix.traineeship.workplacebooking.dto.UserForUserDTO;
import com.irlix.traineeship.workplacebooking.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserForUserDTO toUserForUserDTO(UserEntity userEntity);

    UserForAdminDTO toUserForAdminDTO(UserEntity userEntity);

    UserEntity toUserEntity(UserForAdminDTO userForAdminDTO);

    @Mapping(target = "id", ignore = true)
    void updateUserEntityFromUserForAdminDTO(UserForAdminDTO userForAdminDTO, @MappingTarget UserEntity toUserEntity);
}