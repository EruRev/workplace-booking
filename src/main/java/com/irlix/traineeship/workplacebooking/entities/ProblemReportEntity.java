package com.irlix.traineeship.workplacebooking.entities;

import com.irlix.traineeship.workplacebooking.entities.enums.EnumReport;
import com.irlix.traineeship.workplacebooking.entities.enums.EnumRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "problem_reports")
public class ProblemReportEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "description")
    private String description;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "booking_id")
    private UUID bookingId;

    @Enumerated(EnumType.STRING)
    @Column(name="report_status")
    private EnumReport reportStatus;



    public ProblemReportEntity( LocalDateTime createdAt, String description, UUID userId, UUID bookingId, EnumReport reportStatus) {
        this.createdAt = createdAt;
        this.description = description;
        this.userId = userId;
        this.bookingId = bookingId;
        this.reportStatus = reportStatus;
    }
    public ProblemReportEntity(UUID id, LocalDateTime createdAt, String description, UUID userId, UUID bookingId, EnumReport reportStatus) {
        this.id = id;
        this.createdAt = createdAt;
        this.description = description;
        this.userId = userId;
        this.bookingId = bookingId;
        this.reportStatus = EnumReport.NEW;
    }

    @Override
    public String toString() {
        return "Problem Report" + id + " status is "+ reportStatus.toString();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProblemReportEntity that = (ProblemReportEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
