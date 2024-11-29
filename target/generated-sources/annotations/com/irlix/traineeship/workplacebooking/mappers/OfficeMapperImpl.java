package com.irlix.traineeship.workplacebooking.mappers;

import com.irlix.traineeship.workplacebooking.dto.OfficeForAdminDTO;
import com.irlix.traineeship.workplacebooking.dto.OfficeForOfficeDTO;
import com.irlix.traineeship.workplacebooking.entities.OfficeEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-03T10:56:05+0300",
    comments = "version: 1.6.0, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class OfficeMapperImpl implements OfficeMapper {

    @Override
    public OfficeForAdminDTO toOfficeForAdminDTO(OfficeEntity officeEntity) {
        if ( officeEntity == null ) {
            return null;
        }

        String address = null;
        String name = null;

        address = officeEntity.getAddress();
        name = officeEntity.getName();

        boolean isDeleted = false;

        OfficeForAdminDTO officeForAdminDTO = new OfficeForAdminDTO( address, name, isDeleted );

        return officeForAdminDTO;
    }

    @Override
    public OfficeEntity toOfficeEntity(OfficeForAdminDTO officeForAdminDTO) {
        if ( officeForAdminDTO == null ) {
            return null;
        }

        OfficeEntity officeEntity = new OfficeEntity();

        officeEntity.setAddress( officeForAdminDTO.address() );
        officeEntity.setName( officeForAdminDTO.name() );

        return officeEntity;
    }

    @Override
    public void updateOfficeEntityFromOfficeForAdminDTO(OfficeForAdminDTO officeForAdminDTO, OfficeEntity toOfficeEntity) {
        if ( officeForAdminDTO == null ) {
            return;
        }

        toOfficeEntity.setAddress( officeForAdminDTO.address() );
        toOfficeEntity.setName( officeForAdminDTO.name() );
    }

    @Override
    public OfficeForOfficeDTO toOfficeForOfficeDTO(OfficeEntity officeEntity) {
        if ( officeEntity == null ) {
            return null;
        }

        String officeName = null;
        String officeAddress = null;

        officeName = officeEntity.getName();
        officeAddress = officeEntity.getAddress();

        OfficeForOfficeDTO officeForOfficeDTO = new OfficeForOfficeDTO( officeName, officeAddress );

        return officeForOfficeDTO;
    }
}
