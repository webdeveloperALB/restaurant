package al.student.controller;

import al.student.model.Nota;
import al.student.service.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notat")
@CrossOrigin(origins = "*")
public class NotaController {

    @Autowired
    private NotaService notaService;

    @GetMapping
    public ResponseEntity<List<Nota>> getAllNotat(
            @RequestParam(required = false) UUID regjistrimId) {
        try {
            List<Nota> notat;
            if (regjistrimId != null) {
                notat = notaService.getNotatByRegjistrimId(regjistrimId);
            } else {
                notat = notaService.getAllNotat();
            }
            return ResponseEntity.ok(notat);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Nota> getNotaById(@PathVariable UUID id) {
        try {
            return notaService.getNotaById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Nota> createNota(@RequestBody Nota nota) {
        try {
            Nota created = notaService.createNota(nota);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Nota> updateNota(
            @PathVariable UUID id, @RequestBody Nota nota) {
        try {
            Nota updated = notaService.updateNota(id, nota);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNota(@PathVariable UUID id) {
        try {
            notaService.deleteNota(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/mesatare/{regjistrimId}")
    public ResponseEntity<BigDecimal> getAverageByRegjistrimId(
            @PathVariable UUID regjistrimId) {
        try {
            BigDecimal average = notaService.getAverageByRegjistrimId(regjistrimId);
            return ResponseEntity.ok(average);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
