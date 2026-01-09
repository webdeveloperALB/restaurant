package al.student.service;

import al.student.model.Nota;
import al.student.model.Regjistrim;
import al.student.repository.NotaRepository;
import al.student.repository.RegjistrimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotaService {

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private RegjistrimRepository regjistrimRepository;

    public List<Nota> getAllNotat() {
        List<Nota> notat = notaRepository.findAll();
        enrichNotat(notat);
        return notat;
    }

    public Optional<Nota> getNotaById(UUID id) {
        Optional<Nota> nota = notaRepository.findById(id);
        nota.ifPresent(this::enrichNota);
        return nota;
    }

    public List<Nota> getNotatByRegjistrimId(UUID regjistrimId) {
        List<Nota> notat = notaRepository.findByRegjistrimId(regjistrimId);
        enrichNotat(notat);
        return notat;
    }

    public Nota createNota(Nota nota) {
        Optional<Regjistrim> regjistrim = regjistrimRepository.findById(nota.getRegjistrimId());
        if (regjistrim.isEmpty()) {
            throw new RuntimeException("Regjistrimi nuk u gjet");
        }
        return notaRepository.save(nota);
    }

    public Nota updateNota(UUID id, Nota nota) {
        Optional<Nota> existing = notaRepository.findById(id);
        if (existing.isEmpty()) {
            throw new RuntimeException("Nota nuk u gjet");
        }
        nota.setId(id);
        return notaRepository.save(nota);
    }

    public void deleteNota(UUID id) {
        if (notaRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Nota nuk u gjet");
        }
        notaRepository.deleteById(id);
    }

    public BigDecimal getAverageByRegjistrimId(UUID regjistrimId) {
        return notaRepository.getAverageByRegjistrimId(regjistrimId);
    }

    private void enrichNotat(List<Nota> notat) {
        for (Nota nota : notat) {
            enrichNota(nota);
        }
    }

    private void enrichNota(Nota nota) {
        Optional<Regjistrim> regjistrim = regjistrimRepository.findById(nota.getRegjistrimId());
        regjistrim.ifPresent(nota::setRegjistrim);
    }
}
