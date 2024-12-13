package com.irlix.traineeship.workplacebooking.mappers;

import com.irlix.traineeship.workplacebooking.dto.UserForAdminDTO;
import com.irlix.traineeship.workplacebooking.dto.UserForUserDTO;
import com.irlix.traineeship.workplacebooking.entities.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-13T09:54:01+0300",
    comments = "version: 1.6.0, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserForUserDTO toUserForUserDTO(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        String fullName = null;
        String phoneNumber = null;
        String email = null;

        fullName = userEntity.getFullName();
        phoneNumber = userEntity.getPhoneNumber();
        email = userEntity.getEmail();

        UserForUserDTO userForUserDTO = new UserForUserDTO( fullName, phoneNumber, email );

        return userForUserDTO;
    }

    @Override
    public UserForAdminDTO toUserForAdminDTO(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        String fullName = null;
        String phoneNumber = null;
        String email = null;
        Boolean isDeleted = null;

        fullName = userEntity.getFullName();
        phoneNumber = userEntity.getPhoneNumber();
        email = userEntity.getEmail();
        isDeleted = userEntity.getIsDeleted();

        UserForAdminDTO userForAdminDTO = new UserForAdminDTO( fullName, phoneNumber, email, isDeleted );

        return userForAdminDTO;
    }

    @Override
    public UserEntity toUserEntity(UserForAdminDTO userForAdminDTO) {
        if ( userForAdminDTO == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setFullName( userForAdminDTO.fullName() );
        userEntity.setPhoneNumber( userForAdminDTO.phoneNumber() );
        userEntity.setEmail( userForAdminDTO.email() );
        userEntity.setIsDeleted( userForAdminDTO.isDeleted() );

        return userEntity;
    }

    @Override
    public void updateUserEntityFromUserForAdminDTO(UserForAdminDTO userForAdminDTO, UserEntity toUserEntity) {
        if ( userForAdminDTO == null ) {
            return;
        }

        toUserEntity.setFullName( userForAdminDTO.fullName() );
        toUserEntity.setPhoneNumber( userForAdminDTO.phoneNumber() );
        toUserEntity.setEmail( userForAdminDTO.email() );
        toUserEntity.setIsDeleted( userForAdminDTO.isDeleted() );
    }
}
