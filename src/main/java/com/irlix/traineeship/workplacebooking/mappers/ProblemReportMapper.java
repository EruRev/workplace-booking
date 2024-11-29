package com.irlix.traineeship.workplacebooking.mappers;


import com.irlix.traineeship.workplacebooking.dto.ProblemReportForUserDTO;
import com.irlix.traineeship.workplacebooking.entities.ProblemReportEntity;
import com.irlix.traineeship.workplacebooking.entities.enums.EnumReport;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProblemReportMapper {
    ProblemReportForUserDTO toProblemReportForUserDTO(ProblemReportEntity problemReport);

    ProblemReportEntity toProblemReportEntity(ProblemReportForUserDTO problemReportDTO);

    void updateProblemReportEntityFromProblemReportForUserDTO(ProblemReportForUserDTO problemReportDTO, @MappingTarget ProblemReportEntity problemReport);
    default ProblemReportEntity deleteProblemReportEntityFromProblemReportForUserDTO(ProblemReportForUserDTO problemReportDTO, @MappingTarget ProblemReportEntity problemReport)
    {
        problemReport.setReportStatus(EnumReport.DELETED);
        return problemReport;
    };
}
