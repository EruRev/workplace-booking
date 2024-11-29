package com.irlix.traineeship.workplacebooking.repositories;

import com.irlix.traineeship.workplacebooking.entities.ProblemReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProblemReportRepository extends JpaRepository<ProblemReportEntity, UUID> {
}
