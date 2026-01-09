package al.student.repository;

import al.student.model.Nota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class NotaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Nota> notaRowMapper = new RowMapper<Nota>() {
        @Override
        public Nota mapRow(ResultSet rs, int rowNum) throws SQLException {
            Nota nota = new Nota();
            nota.setId((UUID) rs.getObject("id"));
            nota.setRegjistrimId((UUID) rs.getObject("regjistrim_id"));
            nota.setNota(rs.getBigDecimal("nota"));
            nota.setLlojiProvimit(rs.getString("lloji_provimit"));
            nota.setDataProvimit(rs.getObject("data_provimit", LocalDate.class));
            nota.setKomente(rs.getString("komente"));
            nota.setKrijuarMe(rs.getTimestamp("krijuar_me").toLocalDateTime());
            nota.setPerditsuarMe(rs.getTimestamp("perditesuar_me").toLocalDateTime());
            return nota;
        }
    };

    public List<Nota> findAll() {
        String sql = "SELECT * FROM notat ORDER BY data_provimit DESC";
        return jdbcTemplate.query(sql, notaRowMapper);
    }

    public Optional<Nota> findById(UUID id) {
        String sql = "SELECT * FROM notat WHERE id = ?";
        List<Nota> results = jdbcTemplate.query(sql, notaRowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public List<Nota> findByRegjistrimId(UUID regjistrimId) {
        String sql = "SELECT * FROM notat WHERE regjistrim_id = ? ORDER BY data_provimit DESC";
        return jdbcTemplate.query(sql, notaRowMapper, regjistrimId);
    }

    public Nota save(Nota nota) {
        if (nota.getId() == null) {
            return insert(nota);
        } else {
            return update(nota);
        }
    }

    private Nota insert(Nota nota) {
        String sql = "INSERT INTO notat (regjistrim_id, nota, lloji_provimit, data_provimit, komente) " +
                     "VALUES (?, ?, ?, ?, ?) RETURNING id";
        UUID id = jdbcTemplate.queryForObject(sql, UUID.class,
                nota.getRegjistrimId(),
                nota.getNota(),
                nota.getLlojiProvimit(),
                nota.getDataProvimit(),
                nota.getKomente());
        nota.setId(id);
        return nota;
    }

    private Nota update(Nota nota) {
        String sql = "UPDATE notat SET regjistrim_id = ?, nota = ?, lloji_provimit = ?, " +
                     "data_provimit = ?, komente = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                nota.getRegjistrimId(),
                nota.getNota(),
                nota.getLlojiProvimit(),
                nota.getDataProvimit(),
                nota.getKomente(),
                nota.getId());
        return nota;
    }

    public void deleteById(UUID id) {
        String sql = "DELETE FROM notat WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public BigDecimal getAverageByRegjistrimId(UUID regjistrimId) {
        String sql = "SELECT AVG(nota) FROM notat WHERE regjistrim_id = ?";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, regjistrimId);
    }

    public long countByRegjistrimId(UUID regjistrimId) {
        String sql = "SELECT COUNT(*) FROM notat WHERE regjistrim_id = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, regjistrimId);
    }
}
