package com.irlix.traineeship.workplacebooking.controllers;

import com.irlix.traineeship.workplacebooking.dto.ProblemReportForUserDTO;
import com.irlix.traineeship.workplacebooking.services.ProblemReportService;
import com.irlix.traineeship.workplacebooking.services.impl.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/problems")
@RequiredArgsConstructor
@Tag(name = "Заявка на поломку", description = "Заявки на поломку")
public class ProblemReportController {

    private final ProblemReportService problemReportService;

    @GetMapping
    @Operation(summary = "Отображение всех заявок о поломках", description = "Отображает список всех заявок о поломках, с актуальными статусами")
    public ResponseEntity<List<ProblemReportForUserDTO>> getAllProblemReports() {
        List<ProblemReportForUserDTO> allProblemReports = problemReportService.getAllProblemReports();
        return ResponseEntity.ok(allProblemReports);
    }

    @PostMapping("/new")
    @Operation(summary = "Создание новой заявка", description = "Создает новую заявку, принимая время создания, пользователя, место")
    public ResponseEntity<ProblemReportForUserDTO> createProblemReport(@RequestBody @Valid ProblemReportForUserDTO problemReportDTO, UserDetailsImpl currentUser) {
        ProblemReportForUserDTO createdProblemReport = problemReportService.createProblemReport(problemReportDTO,currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProblemReport);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Отображение заявки", description = "Отображает заявку по id и всю информацию о ней")
    public ResponseEntity<ProblemReportForUserDTO> getProblemReportById(@PathVariable UUID id) {
        ProblemReportForUserDTO problemReport = problemReportService.getProblemReportById(id);
        return ResponseEntity.ok(problemReport);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование заявки", description = "Позволяет изменить поля заявки")
    public ResponseEntity<ProblemReportForUserDTO> updateProblemReport(@PathVariable UUID id, @RequestBody @Valid ProblemReportForUserDTO problemReportDTO) {
        ProblemReportForUserDTO updatedProblemReport = problemReportService.updateProblemReport(id, problemReportDTO);
        return ResponseEntity.ok(updatedProblemReport);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление заявки", description = "Удаляет заявку")
    public ResponseEntity<Void> deleteProblemReport(@PathVariable UUID id,@RequestBody @Valid ProblemReportForUserDTO problemReportDTO) {
        problemReportService.deleteProblemReport(id, problemReportDTO);
        return ResponseEntity.noContent().build();
    }

}
