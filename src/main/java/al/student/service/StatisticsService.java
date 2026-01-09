package al.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class StatisticsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Map<String, Object> getGeneralStatistics() {
        Map<String, Object> stats = new HashMap<>();

        String sqlStudents = "SELECT COUNT(*) FROM studentet";
        stats.put("totaliStudenteve", jdbcTemplate.queryForObject(sqlStudents, Long.class));

        String sqlActiveStudents = "SELECT COUNT(*) FROM studentet WHERE aktiv = true";
        stats.put("studentetAktiv", jdbcTemplate.queryForObject(sqlActiveStudents, Long.class));

        String sqlSubjects = "SELECT COUNT(*) FROM lendet";
        stats.put("totaliLendeve", jdbcTemplate.queryForObject(sqlSubjects, Long.class));

        String sqlEnrollments = "SELECT COUNT(*) FROM regjistrime";
        stats.put("totaliRegjistrimeve", jdbcTemplate.queryForObject(sqlEnrollments, Long.class));

        String sqlGrades = "SELECT COUNT(*) FROM notat";
        stats.put("totaliNotave", jdbcTemplate.queryForObject(sqlGrades, Long.class));

        return stats;
    }

    public Map<String, Object> getStudentStatistics(UUID studentId) {
        Map<String, Object> stats = new HashMap<>();

        String sqlTotalEnrollments = "SELECT COUNT(*) FROM regjistrime WHERE student_id = ?";
        stats.put("totaliLendeve", jdbcTemplate.queryForObject(sqlTotalEnrollments, Long.class, studentId));

        String sqlTotalGrades =
            "SELECT COUNT(*) FROM notat n " +
            "JOIN regjistrime r ON n.regjistrim_id = r.id " +
            "WHERE r.student_id = ?";
        stats.put("totaliNotave", jdbcTemplate.queryForObject(sqlTotalGrades, Long.class, studentId));

        String sqlAverageGrade =
            "SELECT AVG(n.nota) FROM notat n " +
            "JOIN regjistrime r ON n.regjistrim_id = r.id " +
            "WHERE r.student_id = ?";
        BigDecimal avgGrade = jdbcTemplate.queryForObject(sqlAverageGrade, BigDecimal.class, studentId);
        stats.put("mesatareNotave", avgGrade != null ? avgGrade : BigDecimal.ZERO);

        String sqlGradesByYear =
            "SELECT r.viti_akademik, AVG(n.nota) as mesatare " +
            "FROM notat n " +
            "JOIN regjistrime r ON n.regjistrim_id = r.id " +
            "WHERE r.student_id = ? " +
            "GROUP BY r.viti_akademik " +
            "ORDER BY r.viti_akademik DESC";
        List<Map<String, Object>> gradesByYear = jdbcTemplate.queryForList(sqlGradesByYear, studentId);
        stats.put("notatSipasVitit", gradesByYear);

        return stats;
    }

    public List<Map<String, Object>> getAcademicHistory(UUID studentId) {
        String sql =
            "SELECT " +
            "  r.viti_akademik, " +
            "  r.semestri, " +
            "  l.emri as emri_lendes, " +
            "  l.kodi as kodi_lendes, " +
            "  l.kredite, " +
            "  COALESCE(AVG(n.nota), 0) as mesatare, " +
            "  COUNT(n.id) as numri_notave " +
            "FROM regjistrime r " +
            "JOIN lendet l ON r.lende_id = l.id " +
            "LEFT JOIN notat n ON n.regjistrim_id = r.id " +
            "WHERE r.student_id = ? " +
            "GROUP BY r.id, r.viti_akademik, r.semestri, l.emri, l.kodi, l.kredite " +
            "ORDER BY r.viti_akademik DESC, r.semestri, l.kodi";
        return jdbcTemplate.queryForList(sql, studentId);
    }

    public List<Map<String, Object>> getTopStudents(int limit) {
        String sql =
            "SELECT " +
            "  s.id, " +
            "  s.emri, " +
            "  s.mbiemri, " +
            "  s.email, " +
            "  AVG(n.nota) as mesatare, " +
            "  COUNT(n.id) as numri_notave " +
            "FROM studentet s " +
            "JOIN regjistrime r ON s.id = r.student_id " +
            "JOIN notat n ON r.id = n.regjistrim_id " +
            "WHERE s.aktiv = true " +
            "GROUP BY s.id, s.emri, s.mbiemri, s.email " +
            "HAVING COUNT(n.id) >= 3 " +
            "ORDER BY AVG(n.nota) DESC " +
            "LIMIT ?";
        return jdbcTemplate.queryForList(sql, limit);
    }

    public List<Map<String, Object>> getSubjectStatistics() {
        String sql =
            "SELECT " +
            "  l.id, " +
            "  l.emri, " +
            "  l.kodi, " +
            "  COUNT(DISTINCT r.student_id) as numri_studenteve, " +
            "  COALESCE(AVG(n.nota), 0) as mesatare_notave, " +
            "  COUNT(n.id) as numri_notave " +
            "FROM lendet l " +
            "LEFT JOIN regjistrime r ON l.id = r.lende_id " +
            "LEFT JOIN notat n ON r.id = n.regjistrim_id " +
            "GROUP BY l.id, l.emri, l.kodi " +
            "ORDER BY l.kodi";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getEnrollmentsByYear() {
        String sql =
            "SELECT " +
            "  viti_akademik, " +
            "  COUNT(*) as numri_regjistrimeve, " +
            "  COUNT(DISTINCT student_id) as numri_studenteve " +
            "FROM regjistrime " +
            "GROUP BY viti_akademik " +
            "ORDER BY viti_akademik DESC";
        return jdbcTemplate.queryForList(sql);
    }

    public Map<String, Object> getGradeDistribution() {
        Map<String, Object> distribution = new HashMap<>();

        String sql =
            "SELECT " +
            "  CASE " +
            "    WHEN nota >= 9 THEN 'A (9-10)' " +
            "    WHEN nota >= 8 THEN 'B (8-9)' " +
            "    WHEN nota >= 7 THEN 'C (7-8)' " +
            "    WHEN nota >= 6 THEN 'D (6-7)' " +
            "    ELSE 'F (0-6)' " +
            "  END as kategoria, " +
            "  COUNT(*) as numri " +
            "FROM notat " +
            "GROUP BY kategoria " +
            "ORDER BY kategoria";
        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
        distribution.put("shperndarja", results);

        String sqlAvg = "SELECT AVG(nota) FROM notat";
        BigDecimal avg = jdbcTemplate.queryForObject(sqlAvg, BigDecimal.class);
        distribution.put("mesatareGjithsej", avg != null ? avg : BigDecimal.ZERO);

        return distribution;
    }
}
