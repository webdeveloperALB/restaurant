package al.student.service;

import al.student.model.Lende;
import al.student.repository.LendeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LendeService {

    @Autowired
    private LendeRepository lendeRepository;

    public List<Lende> getAllLende() {
        return lendeRepository.findAll();
    }

    public Optional<Lende> getLendeById(UUID id) {
        return lendeRepository.findById(id);
    }

    public List<Lende> searchLende(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllLende();
        }
        return lendeRepository.search(searchTerm.trim());
    }

    public Lende createLende(Lende lende) {
        if (lendeRepository.existsByKodi(lende.getKodi())) {
            throw new RuntimeException("Lënda me këtë kod ekziston tashmë");
        }
        return lendeRepository.save(lende);
    }

    public Lende updateLende(UUID id, Lende lende) {
        Optional<Lende> existing = lendeRepository.findById(id);
        if (existing.isEmpty()) {
            throw new RuntimeException("Lënda nuk u gjet");
        }
        lende.setId(id);
        return lendeRepository.save(lende);
    }

    public void deleteLende(UUID id) {
        if (lendeRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Lënda nuk u gjet");
        }
        lendeRepository.deleteById(id);
    }

    public long getTotalLende() {
        return lendeRepository.count();
    }

    public List<Lende> getLendeBySemestri(int semestri) {
        return lendeRepository.findBySemestri(semestri);
    }

    public List<Lende> getActiveLende() {
        return lendeRepository.findByAktiv(true);
    }
}
