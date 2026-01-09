package al.student.repository;

import al.student.model.Lende;
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
public class LendeRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Lende> lendeRowMapper = new RowMapper<Lende>() {
        @Override
        public Lende mapRow(ResultSet rs, int rowNum) throws SQLException {
            Lende lende = new Lende();
            lende.setId((UUID) rs.getObject("id"));
            lende.setEmri(rs.getString("emri"));
            lende.setKodi(rs.getString("kodi"));
            lende.setPershkrimi(rs.getString("pershkrimi"));
            lende.setKredite(rs.getInt("kredite"));
            lende.setSemestri(rs.getInt("semestri"));
            lende.setAktiv(rs.getBoolean("aktiv"));
            lende.setKrijuarMe(rs.getTimestamp("krijuar_me").toLocalDateTime());
            lende.setPerditsuarMe(rs.getTimestamp("perditesuar_me").toLocalDateTime());
            return lende;
        }
    };

    public List<Lende> findAll() {
        String sql = "SELECT * FROM lendet ORDER BY kodi";
        return jdbcTemplate.query(sql, lendeRowMapper);
    }

    public Optional<Lende> findById(UUID id) {
        String sql = "SELECT * FROM lendet WHERE id = ?";
        List<Lende> results = jdbcTemplate.query(sql, lendeRowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public List<Lende> search(String searchTerm) {
        String sql = "SELECT * FROM lendet WHERE " +
                     "LOWER(emri) LIKE LOWER(?) OR " +
                     "LOWER(kodi) LIKE LOWER(?) OR " +
                     "LOWER(pershkrimi) LIKE LOWER(?) " +
                     "ORDER BY kodi";
        String pattern = "%" + searchTerm + "%";
        return jdbcTemplate.query(sql, lendeRowMapper, pattern, pattern, pattern);
    }

    public Lende save(Lende lende) {
        if (lende.getId() == null) {
            return insert(lende);
        } else {
            return update(lende);
        }
    }

    private Lende insert(Lende lende) {
        String sql = "INSERT INTO lendet (emri, kodi, pershkrimi, kredite, semestri, aktiv) " +
                     "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
        UUID id = jdbcTemplate.queryForObject(sql, UUID.class,
                lende.getEmri(),
                lende.getKodi(),
                lende.getPershkrimi(),
                lende.getKredite(),
                lende.getSemestri(),
                lende.isAktiv());
        lende.setId(id);
        return lende;
    }

    private Lende update(Lende lende) {
        String sql = "UPDATE lendet SET emri = ?, kodi = ?, pershkrimi = ?, kredite = ?, " +
                     "semestri = ?, aktiv = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                lende.getEmri(),
                lende.getKodi(),
                lende.getPershkrimi(),
                lende.getKredite(),
                lende.getSemestri(),
                lende.isAktiv(),
                lende.getId());
        return lende;
    }

    public void deleteById(UUID id) {
        String sql = "DELETE FROM lendet WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public boolean existsByKodi(String kodi) {
        String sql = "SELECT COUNT(*) FROM lendet WHERE kodi = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, kodi);
        return count != null && count > 0;
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM lendet";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public List<Lende> findBySemestri(int semestri) {
        String sql = "SELECT * FROM lendet WHERE semestri = ? ORDER BY kodi";
        return jdbcTemplate.query(sql, lendeRowMapper, semestri);
    }

    public List<Lende> findByAktiv(boolean aktiv) {
        String sql = "SELECT * FROM lendet WHERE aktiv = ? ORDER BY kodi";
        return jdbcTemplate.query(sql, lendeRowMapper, aktiv);
    }
}
