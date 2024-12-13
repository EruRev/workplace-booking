package com.irlix.traineeship.workplacebooking.mappers;

import com.irlix.traineeship.workplacebooking.dto.ProblemReportForUserDTO;
import com.irlix.traineeship.workplacebooking.entities.ProblemReportEntity;
import com.irlix.traineeship.workplacebooking.entities.enums.EnumReport;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-13T09:54:01+0300",
    comments = "version: 1.6.0, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class ProblemReportMapperImpl implements ProblemReportMapper {

    @Override
    public ProblemReportForUserDTO toProblemReportForUserDTO(ProblemReportEntity problemReport) {
        if ( problemReport == null ) {
            return null;
        }

        LocalDateTime createdAt = null;
        String description = null;
        UUID userId = null;

        createdAt = problemReport.getCreatedAt();
        description = problemReport.getDescription();
        userId = problemReport.getUserId();

        UUID workplaceId = null;
        EnumReport report = null;

        ProblemReportForUserDTO problemReportForUserDTO = new ProblemReportForUserDTO( createdAt, description, workplaceId, userId, report );

        return problemReportForUserDTO;
    }

    @Override
    public ProblemReportEntity toProblemReportEntity(ProblemReportForUserDTO problemReportDTO) {
        if ( problemReportDTO == null ) {
            return null;
        }

        ProblemReportEntity problemReportEntity = new ProblemReportEntity();

        problemReportEntity.setCreatedAt( problemReportDTO.createdAt() );
        problemReportEntity.setDescription( problemReportDTO.description() );
        problemReportEntity.setUserId( problemReportDTO.userId() );

        return problemReportEntity;
    }

    @Override
    public void updateProblemReportEntityFromProblemReportForUserDTO(ProblemReportForUserDTO problemReportDTO, ProblemReportEntity problemReport) {
        if ( problemReportDTO == null ) {
            return;
        }

        problemReport.setCreatedAt( problemReportDTO.createdAt() );
        problemReport.setDescription( problemReportDTO.description() );
        problemReport.setUserId( problemReportDTO.userId() );
    }
}
