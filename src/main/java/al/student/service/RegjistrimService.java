package al.student.service;

import al.student.model.Lende;
import al.student.model.Regjistrim;
import al.student.model.Student;
import al.student.repository.LendeRepository;
import al.student.repository.RegjistrimRepository;
import al.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RegjistrimService {

    @Autowired
    private RegjistrimRepository regjistrimRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LendeRepository lendeRepository;

    public List<Regjistrim> getAllRegjistrime() {
        List<Regjistrim> regjistrime = regjistrimRepository.findAll();
        enrichRegjistrime(regjistrime);
        return regjistrime;
    }

    public Optional<Regjistrim> getRegjistrimById(UUID id) {
        Optional<Regjistrim> regjistrim = regjistrimRepository.findById(id);
        regjistrim.ifPresent(r -> enrichRegjistrim(r));
        return regjistrim;
    }

    public List<Regjistrim> getRegjistrimByStudentId(UUID studentId) {
        List<Regjistrim> regjistrime = regjistrimRepository.findByStudentId(studentId);
        enrichRegjistrime(regjistrime);
        return regjistrime;
    }

    public List<Regjistrim> getRegjistrimByLendeId(UUID lendeId) {
        List<Regjistrim> regjistrime = regjistrimRepository.findByLendeId(lendeId);
        enrichRegjistrime(regjistrime);
        return regjistrime;
    }

    public List<Regjistrim> getRegjistrimByVitiAkademik(String vitiAkademik) {
        List<Regjistrim> regjistrime = regjistrimRepository.findByVitiAkademik(vitiAkademik);
        enrichRegjistrime(regjistrime);
        return regjistrime;
    }

    public Regjistrim createRegjistrim(Regjistrim regjistrim) {
        Optional<Student> student = studentRepository.findById(regjistrim.getStudentId());
        if (student.isEmpty()) {
            throw new RuntimeException("Studenti nuk u gjet");
        }
        Optional<Lende> lende = lendeRepository.findById(regjistrim.getLendeId());
        if (lende.isEmpty()) {
            throw new RuntimeException("Lënda nuk u gjet");
        }
        return regjistrimRepository.save(regjistrim);
    }

    public Regjistrim updateRegjistrim(UUID id, Regjistrim regjistrim) {
        Optional<Regjistrim> existing = regjistrimRepository.findById(id);
        if (existing.isEmpty()) {
            throw new RuntimeException("Regjistrimi nuk u gjet");
        }
        regjistrim.setId(id);
        return regjistrimRepository.save(regjistrim);
    }

    public void deleteRegjistrim(UUID id) {
        if (regjistrimRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Regjistrimi nuk u gjet");
        }
        regjistrimRepository.deleteById(id);
    }

    private void enrichRegjistrime(List<Regjistrim> regjistrime) {
        for (Regjistrim regjistrim : regjistrime) {
            enrichRegjistrim(regjistrim);
        }
    }

    private void enrichRegjistrim(Regjistrim regjistrim) {
        Optional<Student> student = studentRepository.findById(regjistrim.getStudentId());
        student.ifPresent(regjistrim::setStudent);

        Optional<Lende> lende = lendeRepository.findById(regjistrim.getLendeId());
        lende.ifPresent(regjistrim::setLende);
    }
}
