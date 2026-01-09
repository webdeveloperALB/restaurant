package al.student.repository;

import al.student.model.Perdoruesi;
import al.student.model.Role;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PerdoruesiRepository {
    private final JdbcTemplate jdbcTemplate;

    public PerdoruesiRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class PerdoruesiRowMapper implements RowMapper<Perdoruesi> {
        @Override
        public Perdoruesi mapRow(ResultSet rs, int rowNum) throws SQLException {
            Perdoruesi perdoruesi = new Perdoruesi();
            perdoruesi.setId((UUID) rs.getObject("id"));
            perdoruesi.setEmail(rs.getString("email"));
            perdoruesi.setFjalekalimiHash(rs.getString("fjalekalimi_hash"));
            perdoruesi.setRoliId((UUID) rs.getObject("roli_id"));
            perdoruesi.setEmri(rs.getString("emri"));
            perdoruesi.setMbiemri(rs.getString("mbiemri"));
            perdoruesi.setFotoProfili(rs.getString("foto_profili"));
            perdoruesi.setTelefoni(rs.getString("telefoni"));
            perdoruesi.setAdresa(rs.getString("adresa"));

            Date dataLindjes = rs.getDate("data_lindjes");
            if (dataLindjes != null) {
                perdoruesi.setDataLindjes(dataLindjes.toLocalDate());
            }

            perdoruesi.setAktiv(rs.getBoolean("aktiv"));
            perdoruesi.setEmailVerifikuar(rs.getBoolean("email_verifikuar"));

            if (rs.getTimestamp("last_login") != null) {
                perdoruesi.setLastLogin(rs.getTimestamp("last_login").toLocalDateTime());
            }

            perdoruesi.setKrijuarMe(rs.getTimestamp("krijuar_me").toLocalDateTime());
            perdoruesi.setPerditsuarMe(rs.getTimestamp("perditesuar_me").toLocalDateTime());

            Role role = new Role();
            role.setId((UUID) rs.getObject("role_id"));
            role.setEmri(rs.getString("role_emri"));
            role.setNivelAksesi(rs.getInt("role_nivel_aksesi"));
            perdoruesi.setRole(role);

            return perdoruesi;
        }
    }

    public Optional<Perdoruesi> findByEmail(String email) {
        String sql = """
            SELECT p.*, r.id as role_id, r.emri as role_emri, r.nivel_aksesi as role_nivel_aksesi
            FROM perdoruesit p
            LEFT JOIN rolet r ON p.roli_id = r.id
            WHERE p.email = ?
        """;
        List<Perdoruesi> perdoruesit = jdbcTemplate.query(sql, new PerdoruesiRowMapper(), email);
        return perdoruesit.isEmpty() ? Optional.empty() : Optional.of(perdoruesit.get(0));
    }

    public Optional<Perdoruesi> findById(UUID id) {
        String sql = """
            SELECT p.*, r.id as role_id, r.emri as role_emri, r.nivel_aksesi as role_nivel_aksesi
            FROM perdoruesit p
            LEFT JOIN rolet r ON p.roli_id = r.id
            WHERE p.id = ?
        """;
        List<Perdoruesi> perdoruesit = jdbcTemplate.query(sql, new PerdoruesiRowMapper(), id);
        return perdoruesit.isEmpty() ? Optional.empty() : Optional.of(perdoruesit.get(0));
    }

    public Perdoruesi save(Perdoruesi perdoruesi) {
        if (perdoruesi.getId() == null) {
            return create(perdoruesi);
        } else {
            return update(perdoruesi);
        }
    }

    private Perdoruesi create(Perdoruesi perdoruesi) {
        String sql = """
            INSERT INTO perdoruesit (email, fjalekalimi_hash, roli_id, emri, mbiemri,
                                    telefoni, adresa, data_lindjes, aktiv, email_verifikuar)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id, krijuar_me, perditesuar_me
        """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            perdoruesi.setId((UUID) rs.getObject("id"));
            perdoruesi.setKrijuarMe(rs.getTimestamp("krijuar_me").toLocalDateTime());
            perdoruesi.setPerditsuarMe(rs.getTimestamp("perditesuar_me").toLocalDateTime());
            return perdoruesi;
        }, perdoruesi.getEmail(), perdoruesi.getFjalekalimiHash(), perdoruesi.getRoliId(),
           perdoruesi.getEmri(), perdoruesi.getMbiemri(), perdoruesi.getTelefoni(),
           perdoruesi.getAdresa(), perdoruesi.getDataLindjes(),
           perdoruesi.getAktiv() != null ? perdoruesi.getAktiv() : true,
           perdoruesi.getEmailVerifikuar() != null ? perdoruesi.getEmailVerifikuar() : false);
    }

    private Perdoruesi update(Perdoruesi perdoruesi) {
        String sql = """
            UPDATE perdoruesit
            SET email = ?, emri = ?, mbiemri = ?, telefoni = ?, adresa = ?,
                data_lindjes = ?, foto_profili = ?, aktiv = ?, perditesuar_me = CURRENT_TIMESTAMP
            WHERE id = ?
            RETURNING perditesuar_me
        """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            perdoruesi.setPerditsuarMe(rs.getTimestamp("perditesuar_me").toLocalDateTime());
            return perdoruesi;
        }, perdoruesi.getEmail(), perdoruesi.getEmri(), perdoruesi.getMbiemri(),
           perdoruesi.getTelefoni(), perdoruesi.getAdresa(), perdoruesi.getDataLindjes(),
           perdoruesi.getFotoProfili(), perdoruesi.getAktiv(), perdoruesi.getId());
    }

    public void updateLastLogin(UUID userId) {
        String sql = "UPDATE perdoruesit SET last_login = CURRENT_TIMESTAMP WHERE id = ?";
        jdbcTemplate.update(sql, userId);
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM perdoruesit WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }
}
