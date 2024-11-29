package com.irlix.traineeship.workplacebooking.mappers;

import com.irlix.traineeship.workplacebooking.dto.WorkplaceForAdminDTO;
import com.irlix.traineeship.workplacebooking.dto.WorkplaceForUserDTO;
import com.irlix.traineeship.workplacebooking.entities.WorkplaceEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class WorkplaceMapperTest {
    WorkplaceMapper workplaceMapper = new WorkplaceMapperImpl();

    @Test
    public void toWorkplaceForAdminDTO() {
        UUID workplaceId = UUID.randomUUID();
        UUID workspaceId = UUID.randomUUID();
        WorkplaceEntity workplaceEntity = new WorkplaceEntity(
                workplaceId,
                1,
                "Description",
                workspaceId,
                false
        );
        WorkplaceForAdminDTO expectedWorkplaceForAdminDTO = new WorkplaceForAdminDTO(
                1,
                "Description",
                workspaceId,
                false
        );
        WorkplaceForAdminDTO actualWorkplaceForAdminDTO = workplaceMapper.toWorkplaceForAdminDTO(workplaceEntity);

        Assertions.assertEquals(expectedWorkplaceForAdminDTO, actualWorkplaceForAdminDTO);
    }

    @Test
    public void toWorkplaceForAdminDTOWithNull() {
        WorkplaceForAdminDTO actualWorkplaceForAdminDTO = workplaceMapper.toWorkplaceForAdminDTO(null);

        Assertions.assertNull(actualWorkplaceForAdminDTO);
    }

    @Test
    public void toWorkplaceEntity() {
        UUID workplaceId = UUID.randomUUID();
        WorkplaceForAdminDTO workplaceForAdminDTO = new WorkplaceForAdminDTO(
                1,
                "Description",
                workplaceId,
                false
        );
        WorkplaceEntity expectedWorkplaceEntity = new WorkplaceEntity(
                1,
                "Description",
                workplaceId,
                false
        );
        WorkplaceEntity actualWorkplaceEntity = workplaceMapper.toWorkplaceEntity(workplaceForAdminDTO);

        Assertions.assertEquals(expectedWorkplaceEntity, actualWorkplaceEntity);
    }

    @Test
    public void toWorkplaceEntityWithNull() {
        WorkplaceEntity actualWorkplaceEntity = workplaceMapper.toWorkplaceEntity(null);

        Assertions.assertNull(actualWorkplaceEntity);
    }

    @Test
    public void updateWorkplaceEntityFromWorkplaceForAdminDTO() {
        UUID workplaceId = UUID.randomUUID();
        UUID workspaceId = UUID.randomUUID();
        WorkplaceEntity workplaceEntity = new WorkplaceEntity(
                workplaceId,
                1,
                "Description",
                workspaceId,
                false
        );
        WorkplaceForAdminDTO workplaceForAdminDTO = new WorkplaceForAdminDTO(
                1,
                "New description",
                workspaceId,
                false
        );
        WorkplaceEntity updatedWorkplaceEntity = new WorkplaceEntity(
                workplaceId,
                1,
                "New description",
                workspaceId,
                false
        );
        workplaceMapper.updateWorkplaceEntityFromWorkplaceForAdminDTO(workplaceForAdminDTO, workplaceEntity);

        Assertions.assertEquals(workplaceEntity, updatedWorkplaceEntity);
    }

    @Test
    public void updateWorkplaceEntityFromWorkplaceForAdminDTOWithNull() {
        UUID workplaceId = UUID.randomUUID();
        UUID workspaceId = UUID.randomUUID();
        WorkplaceEntity workplaceEntity = new WorkplaceEntity(
                workplaceId,
                1,
                "Description",
                workspaceId,
                false
        );
        WorkplaceEntity newWorkplaceEntity = new WorkplaceEntity(
                workplaceId,
                1,
                "Description",
                workspaceId,
                false
        );
        workplaceMapper.updateWorkplaceEntityFromWorkplaceForAdminDTO(null, newWorkplaceEntity);

        Assertions.assertEquals(workplaceEntity, newWorkplaceEntity);
    }

    @Test
    public void toWorkplaceForUserDTO() {
        UUID workplaceId = UUID.randomUUID();
        UUID workspaceId = UUID.randomUUID();
        WorkplaceEntity workplaceEntity = new WorkplaceEntity(
                workplaceId,
                1,
                "Description",
                workspaceId,
                false
        );
        WorkplaceForUserDTO expectedWorkplaceForUserDTO = new WorkplaceForUserDTO(
                1,
                "Description",
                workspaceId
        );

        WorkplaceForUserDTO actualWorkplaceForUserDTO = workplaceMapper.toWorkplaceForUserDTO(workplaceEntity);

        Assertions.assertEquals(expectedWorkplaceForUserDTO, actualWorkplaceForUserDTO);
    }

    @Test
    public void toWorkplaceForUserDTOWithNull() {
        WorkplaceEntity workplaceEntity = new WorkplaceEntity();
        WorkplaceForUserDTO expectedWorkplaceForUserDTO =
                new WorkplaceForUserDTO(0, null, null);

        WorkplaceForUserDTO actualWorkplaceForUserDTO = workplaceMapper.toWorkplaceForUserDTO(workplaceEntity);

        Assertions.assertEquals(expectedWorkplaceForUserDTO, actualWorkplaceForUserDTO);
    }
}
