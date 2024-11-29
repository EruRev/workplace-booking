package com.irlix.traineeship.workplacebooking.mappers;

import com.irlix.traineeship.workplacebooking.dto.OfficeForAdminDTO;
import com.irlix.traineeship.workplacebooking.dto.OfficeForOfficeDTO;
import com.irlix.traineeship.workplacebooking.entities.OfficeEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class OfficeMapperTest {
    OfficeMapper officeMapper = new OfficeMapperImpl();

    @Test
    public void toOfficeForAdminDTO() {
        UUID officeId = UUID.randomUUID();
        OfficeEntity officeEntity = new OfficeEntity(
                officeId,
                "Ul'yanovsk, Mira st., 2" ,
                "Gryffindor",
                false
        );
        OfficeForAdminDTO expectedOfficeForAdminDTO = new OfficeForAdminDTO(
                "Ul'yanovsk, Mira st., 2",
                "Gryffindor",
                false
        );
        OfficeForAdminDTO actualOfficeForAdminDTO = officeMapper.toOfficeForAdminDTO(officeEntity);

        Assertions.assertEquals(expectedOfficeForAdminDTO, actualOfficeForAdminDTO);
    }

    @Test
    public void toOfficeForAdminDTOWithNull() {
        OfficeForAdminDTO actualOfficeForAdminDTO = officeMapper.toOfficeForAdminDTO(null);

        Assertions.assertNull(actualOfficeForAdminDTO);
    }

    @Test
    public void toOfficeEntity() {
        OfficeForAdminDTO officeForAdminDTO = new OfficeForAdminDTO(
                "Ul'yanovsk, Mira st., 2",
                "Gryffindor",
                false
        );
        OfficeEntity expectedOfficeEntity = new OfficeEntity(
                "Ul'yanovsk, Mira st., 2" ,
                "Gryffindor",
                false
        );
        OfficeEntity actualOfficeEntity = officeMapper.toOfficeEntity(officeForAdminDTO);

        Assertions.assertEquals(expectedOfficeEntity, actualOfficeEntity);
    }

    @Test
    public void toOfficeEntityWithNull() {
        OfficeEntity actualOfficeEntity = officeMapper.toOfficeEntity(null);

        Assertions.assertNull(actualOfficeEntity);
    }
    @Test
    public void updateOfficeEntityFromOfficeForAdminDTO() {
        OfficeEntity officeEntity = new OfficeEntity(
                "Ul'yanovsk, Mira st., 2",
                "Gryffindor",
                false
        );
        OfficeForAdminDTO officeForAdminDTO = new OfficeForAdminDTO(
                "Ul'yanovsk, Mira st., 2",
                "Slytherin",
                false
        );
        OfficeEntity updatedOfficeEntity = new OfficeEntity(
                "Ul'yanovsk, Mira st., 2",
                "Slytherin",
                false
        );
        officeMapper.updateOfficeEntityFromOfficeForAdminDTO(officeForAdminDTO, officeEntity);

        Assertions.assertEquals(officeEntity, updatedOfficeEntity);
    }

    @Test
    public void updateOfficeEntityFromOfficeForAdminDTOWithNull() {
        OfficeEntity officeEntity = new OfficeEntity(
                "Ul'yanovsk, Mira st., 2",
                "Gryffindor",
                false
        );
        OfficeEntity newOfficeEntity = new OfficeEntity(
                "Ul'yanovsk, Mira st., 2",
                "Gryffindor",
                false
        );
        officeMapper.updateOfficeEntityFromOfficeForAdminDTO(null, newOfficeEntity);

        Assertions.assertEquals(officeEntity, newOfficeEntity);
    }
    @Test
    public void toOfficeForOfficeDTO() throws Exception {
        UUID officeId = UUID.randomUUID();
        OfficeEntity officeEntity = new OfficeEntity(officeId, "Name1", "Address1", false);
        OfficeForOfficeDTO expectedOfficeForOfficeDTO =
                new OfficeForOfficeDTO("Name1","Address1");
        OfficeForOfficeDTO actualOfficeForOfficeDTO = officeMapper.toOfficeForOfficeDTO(officeEntity);
        Assertions.assertEquals(expectedOfficeForOfficeDTO, actualOfficeForOfficeDTO);
    }
    @Test
    public void toOfficeForOfficeDTOWithNull() throws Exception {
        OfficeEntity officeEntity =officeMapper.toOfficeEntity(null) ;
        OfficeForOfficeDTO actualOfficeForOfficeDTO = officeMapper.toOfficeForOfficeDTO(officeEntity);
        Assertions.assertNull(actualOfficeForOfficeDTO);
    }
}
