package al.student.controller;

import al.student.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/statistika")
@CrossOrigin(origins = "*")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/pergjithshme")
    public ResponseEntity<Map<String, Object>> getGeneralStatistics() {
        try {
            Map<String, Object> stats = statisticsService.getGeneralStatistics();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<Map<String, Object>> getStudentStatistics(@PathVariable UUID studentId) {
        try {
            Map<String, Object> stats = statisticsService.getStudentStatistics(studentId);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/historia-akademike/{studentId}")
    public ResponseEntity<List<Map<String, Object>>> getAcademicHistory(@PathVariable UUID studentId) {
        try {
            List<Map<String, Object>> history = statisticsService.getAcademicHistory(studentId);
            return ResponseEntity.ok(history);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/studentet-me-te-mire")
    public ResponseEntity<List<Map<String, Object>>> getTopStudents(
            @RequestParam(defaultValue = "10") int limit) {
        try {
            List<Map<String, Object>> topStudents = statisticsService.getTopStudents(limit);
            return ResponseEntity.ok(topStudents);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/lendet")
    public ResponseEntity<List<Map<String, Object>>> getSubjectStatistics() {
        try {
            List<Map<String, Object>> stats = statisticsService.getSubjectStatistics();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/regjistrime-sipas-vitit")
    public ResponseEntity<List<Map<String, Object>>> getEnrollmentsByYear() {
        try {
            List<Map<String, Object>> enrollments = statisticsService.getEnrollmentsByYear();
            return ResponseEntity.ok(enrollments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/shperndarje-notash")
    public ResponseEntity<Map<String, Object>> getGradeDistribution() {
        try {
            Map<String, Object> distribution = statisticsService.getGradeDistribution();
            return ResponseEntity.ok(distribution);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
