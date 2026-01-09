package al.student.repository;

import al.student.model.Role;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class RoleRepository {
    private final JdbcTemplate jdbcTemplate;

    public RoleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class RoleRowMapper implements RowMapper<Role> {
        @Override
        public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
            Role role = new Role();
            role.setId((UUID) rs.getObject("id"));
            role.setEmri(rs.getString("emri"));
            role.setPershkrimi(rs.getString("pershkrimi"));
            role.setNivelAksesi(rs.getInt("nivel_aksesi"));
            role.setKrijuarMe(rs.getTimestamp("krijuar_me").toLocalDateTime());
            return role;
        }
    }

    public List<Role> findAll() {
        String sql = "SELECT * FROM rolet ORDER BY nivel_aksesi DESC";
        return jdbcTemplate.query(sql, new RoleRowMapper());
    }

    public Optional<Role> findById(UUID id) {
        String sql = "SELECT * FROM rolet WHERE id = ?";
        List<Role> roles = jdbcTemplate.query(sql, new RoleRowMapper(), id);
        return roles.isEmpty() ? Optional.empty() : Optional.of(roles.get(0));
    }

    public Optional<Role> findByEmri(String emri) {
        String sql = "SELECT * FROM rolet WHERE emri = ?";
        List<Role> roles = jdbcTemplate.query(sql, new RoleRowMapper(), emri);
        return roles.isEmpty() ? Optional.empty() : Optional.of(roles.get(0));
    }
}
