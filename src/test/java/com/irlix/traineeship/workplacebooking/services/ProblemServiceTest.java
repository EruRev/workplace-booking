package com.irlix.traineeship.workplacebooking.services;

import com.irlix.traineeship.workplacebooking.dto.ProblemReportForUserDTO;
import com.irlix.traineeship.workplacebooking.entities.ProblemReportEntity;
import com.irlix.traineeship.workplacebooking.entities.enums.EnumReport;
import com.irlix.traineeship.workplacebooking.exceptions.BlankFieldsException;
import com.irlix.traineeship.workplacebooking.exceptions.ResourceNotFoundException;
import com.irlix.traineeship.workplacebooking.mappers.ProblemReportMapper;
import com.irlix.traineeship.workplacebooking.repositories.ProblemReportRepository;
import com.irlix.traineeship.workplacebooking.services.impl.UserDetailsImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProblemServiceTest {
    @InjectMocks
    ProblemReportService problemService;

    @Mock
    ProblemReportRepository problemRepository;

    @Mock
    ProblemReportMapper problemMapper;

    private static class TestReportData {
        UUID reportId1 = UUID.randomUUID();
        UUID reportId2 = UUID.randomUUID();
        UUID userId1 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();
        UUID workplaceId1 = UUID.randomUUID();
        UUID workplaceId2 = UUID.randomUUID();
        ProblemReportEntity reportEntity1 = new ProblemReportEntity(
                reportId1,
                LocalDateTime.now(),
                "problem",
                userId1,
                workplaceId1,
                EnumReport.RESOLVING
        );
        ProblemReportEntity reportEntity2 = new ProblemReportEntity(
                reportId2,
                LocalDateTime.now(),
                "problem",
                userId2,
                workplaceId2,
                EnumReport.RESOLVING
        );
        ProblemReportForUserDTO problemReportForUserDTO1 = new ProblemReportForUserDTO(
                LocalDateTime.now(),
                "problem",
                userId1,
                workplaceId1,
                EnumReport.RESOLVING
        );
        ProblemReportForUserDTO problemReportForUserDTO2 = new ProblemReportForUserDTO(
                LocalDateTime.now(),
                "problem",
                userId2,
                workplaceId2,
                EnumReport.RESOLVING
        );

        List<ProblemReportEntity> allReports = Arrays.asList(reportEntity1,reportEntity2);
        List<ProblemReportForUserDTO> allReportsForUsersDTO = Arrays.asList(problemReportForUserDTO1,problemReportForUserDTO2);
    }
    /*@Test
    public void createReport() {
        TestReportData testReportData = new TestReportData();
        ProblemReportForUserDTO expectedProblemReportForUserDTO = testReportData.problemReportForUserDTO1;
        UserDetailsImpl currentUser = new UserDetailsImpl();
        when(problemRepository.save(any(ProblemReportEntity.class))).thenReturn(testReportData.reportEntity1);
        when(problemMapper.toProblemReportForUserDTO(any(ProblemReportEntity.class))).thenReturn(expectedProblemReportForUserDTO);
        when(problemMapper.toProblemReportEntity(any(ProblemReportForUserDTO.class))).thenReturn(testReportData.reportEntity1);
        ProblemReportForUserDTO actualProblemReportForUserDTO = problemService.createProblemReport(expectedProblemReportForUserDTO,currentUser);

        Assertions.assertEquals(expectedProblemReportForUserDTO, actualProblemReportForUserDTO);
        verify(problemRepository, times(1)).save(any(ProblemReportEntity.class));
        verify(problemMapper, times(1)).toProblemReportForUserDTO(any(ProblemReportEntity.class));
        verify(problemMapper, times(1)).toProblemReportEntity(any(ProblemReportForUserDTO.class));
    }
*/
    @Test
    public void createProblemReportWithMissingParameters() {
        ProblemReportForUserDTO expectedProblemReportForUserDTO = new ProblemReportForUserDTO(
                LocalDateTime.now(),
                null,
                UUID.randomUUID(),
                UUID.randomUUID(),
                EnumReport.CANCELLED
        );
        UserDetailsImpl currentUser = new UserDetailsImpl();
        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            problemService.createProblemReport(expectedProblemReportForUserDTO, currentUser);
        });

        //Assertions.assertEquals("Please describe the problem to continue", thrown.getMessage());
    }

    @Test
    public void showReport() {
        ProblemServiceTest.TestReportData testReportData = new ProblemServiceTest.TestReportData();
        ProblemReportForUserDTO expectedProblemReportForUserDTO = testReportData.problemReportForUserDTO1;

        when(problemMapper.toProblemReportForUserDTO(any(ProblemReportEntity.class))).thenReturn(expectedProblemReportForUserDTO);
        when(problemRepository.findById(testReportData.reportId1)).thenReturn(Optional.of(testReportData.reportEntity1));
        ProblemReportForUserDTO actualProblemReportForUserDTO = problemService.getProblemReportById(testReportData.reportId1);

        Assertions.assertNotNull(actualProblemReportForUserDTO);
        Assertions.assertEquals(expectedProblemReportForUserDTO, actualProblemReportForUserDTO);
    }

    @Test
    public void showReportNotFound() {
        UUID reportId = UUID.randomUUID();

        ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            when(problemRepository.findById(reportId)).thenReturn(Optional.empty());
            problemService.getProblemReportById(reportId);
        });

        Assertions.assertEquals("Problem report not found", thrown.getMessage());
    }

    @Test
    public void showAllReports() {
        ProblemServiceTest.TestReportData testReportData = new ProblemServiceTest.TestReportData();
        List<ProblemReportEntity> expectedProblemReports = testReportData.allReports;
        List<ProblemReportForUserDTO> expectedReportsDTO = testReportData.allReportsForUsersDTO;

        when(problemMapper.toProblemReportForUserDTO(testReportData.reportEntity1))
                .thenReturn(testReportData.problemReportForUserDTO1);
        when(problemMapper.toProblemReportForUserDTO(testReportData.reportEntity2))
                .thenReturn(testReportData.problemReportForUserDTO2);

        when(problemRepository.findAll()).thenReturn(expectedProblemReports);
        List<ProblemReportForUserDTO> actualReports = problemService.getAllProblemReports();

        Assertions.assertNotNull(actualReports);
        Assertions.assertEquals(expectedReportsDTO, actualReports);
    }

    @Test
    public void showAllReportsNotFound() {
        List<ProblemReportEntity> expectedProblemReports = new ArrayList<>(0);
        List<ProblemReportForUserDTO> expectedProblemReportsDTO = new ArrayList<>(0);

        when(problemRepository.findAll()).thenReturn(expectedProblemReports);
        List<ProblemReportForUserDTO> actualProblemReportDorUserDTO = problemService.getAllProblemReports();

        Assertions.assertEquals(expectedProblemReportsDTO, actualProblemReportDorUserDTO);
    }
    @Test
    public void updateReport() {
        ProblemServiceTest.TestReportData testReportData = new ProblemServiceTest.TestReportData();
        ProblemReportForUserDTO expectedProblemReportForUserDTO = testReportData.problemReportForUserDTO1;

        when(problemRepository.findById(testReportData.reportId1)).thenReturn(Optional.of(testReportData.reportEntity1));
        when(problemMapper.toProblemReportForUserDTO(testReportData.reportEntity1)).thenReturn(expectedProblemReportForUserDTO);
        when(problemRepository.save(testReportData.reportEntity1)).thenReturn(testReportData.reportEntity1);
        ProblemReportForUserDTO actualReportForUserDTO = problemService.updateProblemReport(testReportData.reportId1,
                expectedProblemReportForUserDTO);

        Assertions.assertEquals(expectedProblemReportForUserDTO, actualReportForUserDTO);
    }

}
