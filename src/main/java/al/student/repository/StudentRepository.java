package al.student.repository;

import al.student.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class StudentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Student> studentRowMapper = new RowMapper<Student>() {
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student student = new Student();
            student.setId((UUID) rs.getObject("id"));
            student.setEmri(rs.getString("emri"));
            student.setMbiemri(rs.getString("mbiemri"));
            student.setEmail(rs.getString("email"));
            student.setDataLindjes(rs.getObject("data_lindjes", LocalDate.class));
            student.setAdresa(rs.getString("adresa"));
            student.setTelefoni(rs.getString("telefoni"));
            student.setDataRegjistrimit(rs.getObject("data_regjistrimit", LocalDate.class));
            student.setAktiv(rs.getBoolean("aktiv"));
            student.setKrijuarMe(rs.getTimestamp("krijuar_me").toLocalDateTime());
            student.setPerditsuarMe(rs.getTimestamp("perditesuar_me").toLocalDateTime());
            return student;
        }
    };

    public List<Student> findAll() {
        String sql = "SELECT * FROM studentet ORDER BY mbiemri, emri";
        return jdbcTemplate.query(sql, studentRowMapper);
    }

    public Optional<Student> findById(UUID id) {
        String sql = "SELECT * FROM studentet WHERE id = ?";
        List<Student> results = jdbcTemplate.query(sql, studentRowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public List<Student> search(String searchTerm) {
        String sql = "SELECT * FROM studentet WHERE " +
                     "LOWER(emri) LIKE LOWER(?) OR " +
                     "LOWER(mbiemri) LIKE LOWER(?) OR " +
                     "LOWER(email) LIKE LOWER(?) " +
                     "ORDER BY mbiemri, emri";
        String pattern = "%" + searchTerm + "%";
        return jdbcTemplate.query(sql, studentRowMapper, pattern, pattern, pattern);
    }

    public Student save(Student student) {
        if (student.getId() == null) {
            return insert(student);
        } else {
            return update(student);
        }
    }

    private Student insert(Student student) {
        String sql = "INSERT INTO studentet (emri, mbiemri, email, data_lindjes, adresa, telefoni, data_regjistrimit, aktiv) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
        UUID id = jdbcTemplate.queryForObject(sql, UUID.class,
                student.getEmri(),
                student.getMbiemri(),
                student.getEmail(),
                student.getDataLindjes(),
                student.getAdresa(),
                student.getTelefoni(),
                student.getDataRegjistrimit(),
                student.isAktiv());
        student.setId(id);
        return student;
    }

    private Student update(Student student) {
        String sql = "UPDATE studentet SET emri = ?, mbiemri = ?, email = ?, data_lindjes = ?, " +
                     "adresa = ?, telefoni = ?, data_regjistrimit = ?, aktiv = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                student.getEmri(),
                student.getMbiemri(),
                student.getEmail(),
                student.getDataLindjes(),
                student.getAdresa(),
                student.getTelefoni(),
                student.getDataRegjistrimit(),
                student.isAktiv(),
                student.getId());
        return student;
    }

    public void deleteById(UUID id) {
        String sql = "DELETE FROM studentet WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM studentet WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM studentet";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public List<Student> findByAktiv(boolean aktiv) {
        String sql = "SELECT * FROM studentet WHERE aktiv = ? ORDER BY mbiemri, emri";
        return jdbcTemplate.query(sql, studentRowMapper, aktiv);
    }
}
