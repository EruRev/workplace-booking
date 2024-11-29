package com.irlix.traineeship.workplacebooking.mappers;

import com.irlix.traineeship.workplacebooking.dto.ProblemReportForUserDTO;
import com.irlix.traineeship.workplacebooking.entities.ProblemReportEntity;

import com.irlix.traineeship.workplacebooking.entities.enums.EnumReport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReportMapperTest {
    ProblemReportMapper reportMapper = new ProblemReportMapperImpl() {
        @Test
        public void toProblemReportForUserDTO() {
            UUID reportId = UUID.randomUUID();
            UUID userId = UUID.randomUUID();
            UUID workplaceId = UUID.randomUUID();
            ProblemReportEntity reportEntity = new ProblemReportEntity(reportId, LocalDateTime.now(), "computer", userId, workplaceId, EnumReport.FINISHED);
            ProblemReportForUserDTO expectedProblemReportForUserDTO = new ProblemReportForUserDTO(LocalDateTime.now(), "computer", userId, workplaceId, EnumReport.FINISHED);

            ProblemReportForUserDTO actualUserForUserDTO = reportMapper.toProblemReportForUserDTO(reportEntity);

            Assertions.assertEquals(expectedProblemReportForUserDTO, actualUserForUserDTO);
            ;
        }

        @Test
        public void toProblemReportForUserDTOWithNull() {
            ProblemReportForUserDTO actualProblemReportForUserDTO = reportMapper.toProblemReportForUserDTO(null);

            Assertions.assertNull(actualProblemReportForUserDTO);
        }
/*
        @Test
        public void updateProblemReportEntityFromProblemReportForAdminDTO() {

        }
        @Test public void updateProblemReportEntityFromProblemReportForAdminDTOWithNull() {

        }
 */
    };
}
