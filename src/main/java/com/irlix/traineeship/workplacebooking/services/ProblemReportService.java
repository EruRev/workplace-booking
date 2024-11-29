package com.irlix.traineeship.workplacebooking.services;

import com.irlix.traineeship.workplacebooking.dto.ProblemReportForUserDTO;
import com.irlix.traineeship.workplacebooking.entities.BookingEntity;
import com.irlix.traineeship.workplacebooking.entities.ProblemReportEntity;
import com.irlix.traineeship.workplacebooking.exceptions.ResourceNotFoundException;
import com.irlix.traineeship.workplacebooking.mappers.ProblemReportMapper;
import com.irlix.traineeship.workplacebooking.repositories.ProblemReportRepository;
import com.irlix.traineeship.workplacebooking.repositories.BookingRepository;
import com.irlix.traineeship.workplacebooking.services.impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProblemReportService {
    private final ProblemReportRepository problemReportRepository;
    private final ProblemReportMapper problemReportMapper;
    private final BookingRepository bookingRepository;


    public ProblemReportForUserDTO createProblemReport(ProblemReportForUserDTO problemReportDTO, UserDetailsImpl currentUser) {
        BookingEntity bookingEntity = bookingRepository.findCurrentBooking(currentUser.getId(), LocalDateTime.now());
        if (Objects.isNull(bookingEntity) || !bookingEntity.isConfirmed()) {
            throw new ResourceNotFoundException("Booking not found");
        } else {
            ProblemReportEntity problemReportEntity = problemReportMapper.toProblemReportEntity(problemReportDTO);
            ProblemReportEntity savedEntity = problemReportRepository.save(problemReportEntity);
            return problemReportMapper.toProblemReportForUserDTO(savedEntity);

        }
    }
    public ProblemReportForUserDTO getProblemReportById(UUID id) {
        ProblemReportEntity problemReportEntity = problemReportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Problem report not found"));
        return problemReportMapper.toProblemReportForUserDTO(problemReportEntity);
    }

    public ProblemReportForUserDTO updateProblemReport(UUID id, ProblemReportForUserDTO problemReportDTO) {
        ProblemReportEntity existingProblemReport = problemReportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Problem report not found"));
        problemReportMapper.updateProblemReportEntityFromProblemReportForUserDTO(problemReportDTO, existingProblemReport);
        ProblemReportEntity updatedProblemReport = problemReportRepository.save(existingProblemReport);
        return problemReportMapper.toProblemReportForUserDTO(updatedProblemReport);
    }

    public void deleteProblemReport(UUID id, ProblemReportForUserDTO problemReportDTO) {
        ProblemReportEntity existingProblemReport = problemReportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Problem report not found"));
        problemReportMapper.deleteProblemReportEntityFromProblemReportForUserDTO(problemReportDTO, existingProblemReport);
    }

    public List<ProblemReportForUserDTO> getAllProblemReports() {
        return problemReportRepository.findAll()
                .stream()
                .map(problemReportMapper::toProblemReportForUserDTO)
                .collect(Collectors.toList());
    }
}
