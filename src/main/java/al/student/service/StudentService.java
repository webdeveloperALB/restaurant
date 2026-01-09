package al.student.service;

import al.student.model.Student;
import al.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(UUID id) {
        return studentRepository.findById(id);
    }

    public List<Student> searchStudents(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllStudents();
        }
        return studentRepository.search(searchTerm.trim());
    }

    public Student createStudent(Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new RuntimeException("Studenti me këtë email ekziston tashmë");
        }
        return studentRepository.save(student);
    }

    public Student updateStudent(UUID id, Student student) {
        Optional<Student> existing = studentRepository.findById(id);
        if (existing.isEmpty()) {
            throw new RuntimeException("Studenti nuk u gjet");
        }
        student.setId(id);
        return studentRepository.save(student);
    }

    public void deleteStudent(UUID id) {
        if (studentRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Studenti nuk u gjet");
        }
        studentRepository.deleteById(id);
    }

    public long getTotalStudents() {
        return studentRepository.count();
    }

    public List<Student> getActiveStudents() {
        return studentRepository.findByAktiv(true);
    }

    public List<Student> getInactiveStudents() {
        return studentRepository.findByAktiv(false);
    }
}
