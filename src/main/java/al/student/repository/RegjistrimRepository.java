package al.student.repository;

import al.student.model.Regjistrim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class RegjistrimRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Regjistrim> regjistrimRowMapper = new RowMapper<Regjistrim>() {
        @Override
        public Regjistrim mapRow(ResultSet rs, int rowNum) throws SQLException {
            Regjistrim regjistrim = new Regjistrim();
            regjistrim.setId((UUID) rs.getObject("id"));
            regjistrim.setStudentId((UUID) rs.getObject("student_id"));
            regjistrim.setLendeId((UUID) rs.getObject("lende_id"));
            regjistrim.setVitiAkademik(rs.getString("viti_akademik"));
            regjistrim.setSemestri(rs.getInt("semestri"));
            regjistrim.setDataRegjistrimit(rs.getTimestamp("data_regjistrimit").toLocalDateTime());
            regjistrim.setAktiv(rs.getBoolean("aktiv"));
            regjistrim.setKrijuarMe(rs.getTimestamp("krijuar_me").toLocalDateTime());
            return regjistrim;
        }
    };

    public List<Regjistrim> findAll() {
        String sql = "SELECT * FROM regjistrime ORDER BY data_regjistrimit DESC";
        return jdbcTemplate.query(sql, regjistrimRowMapper);
    }

    public Optional<Regjistrim> findById(UUID id) {
        String sql = "SELECT * FROM regjistrime WHERE id = ?";
        List<Regjistrim> results = jdbcTemplate.query(sql, regjistrimRowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public List<Regjistrim> findByStudentId(UUID studentId) {
        String sql = "SELECT * FROM regjistrime WHERE student_id = ? ORDER BY viti_akademik DESC, semestri";
        return jdbcTemplate.query(sql, regjistrimRowMapper, studentId);
    }

    public List<Regjistrim> findByLendeId(UUID lendeId) {
        String sql = "SELECT * FROM regjistrime WHERE lende_id = ? ORDER BY viti_akademik DESC";
        return jdbcTemplate.query(sql, regjistrimRowMapper, lendeId);
    }

    public List<Regjistrim> findByVitiAkademik(String vitiAkademik) {
        String sql = "SELECT * FROM regjistrime WHERE viti_akademik = ? ORDER BY semestri";
        return jdbcTemplate.query(sql, regjistrimRowMapper, vitiAkademik);
    }

    public Regjistrim save(Regjistrim regjistrim) {
        if (regjistrim.getId() == null) {
            return insert(regjistrim);
        } else {
            return update(regjistrim);
        }
    }

    private Regjistrim insert(Regjistrim regjistrim) {
        String sql = "INSERT INTO regjistrime (student_id, lende_id, viti_akademik, semestri, aktiv) " +
                     "VALUES (?, ?, ?, ?, ?) RETURNING id";
        UUID id = jdbcTemplate.queryForObject(sql, UUID.class,
                regjistrim.getStudentId(),
                regjistrim.getLendeId(),
                regjistrim.getVitiAkademik(),
                regjistrim.getSemestri(),
                regjistrim.isAktiv());
        regjistrim.setId(id);
        return regjistrim;
    }

    private Regjistrim update(Regjistrim regjistrim) {
        String sql = "UPDATE regjistrime SET student_id = ?, lende_id = ?, viti_akademik = ?, " +
                     "semestri = ?, aktiv = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                regjistrim.getStudentId(),
                regjistrim.getLendeId(),
                regjistrim.getVitiAkademik(),
                regjistrim.getSemestri(),
                regjistrim.isAktiv(),
                regjistrim.getId());
        return regjistrim;
    }

    public void deleteById(UUID id) {
        String sql = "DELETE FROM regjistrime WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public long countByStudentId(UUID studentId) {
        String sql = "SELECT COUNT(*) FROM regjistrime WHERE student_id = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, studentId);
    }

    public long countByLendeId(UUID lendeId) {
        String sql = "SELECT COUNT(*) FROM regjistrime WHERE lende_id = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, lendeId);
    }
}
