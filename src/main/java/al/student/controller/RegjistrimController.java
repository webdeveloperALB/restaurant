package al.student.controller;

import al.student.model.Regjistrim;
import al.student.service.RegjistrimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/regjistrime")
@CrossOrigin(origins = "*")
public class RegjistrimController {

    @Autowired
    private RegjistrimService regjistrimService;

    @GetMapping
    public ResponseEntity<List<Regjistrim>> getAllRegjistrime(
            @RequestParam(required = false) UUID studentId,
            @RequestParam(required = false) UUID lendeId,
            @RequestParam(required = false) String vitiAkademik) {
        try {
            List<Regjistrim> regjistrime;
            if (studentId != null) {
                regjistrime = regjistrimService.getRegjistrimByStudentId(studentId);
            } else if (lendeId != null) {
                regjistrime = regjistrimService.getRegjistrimByLendeId(lendeId);
            } else if (vitiAkademik != null) {
                regjistrime = regjistrimService.getRegjistrimByVitiAkademik(vitiAkademik);
            } else {
                regjistrime = regjistrimService.getAllRegjistrime();
            }
            return ResponseEntity.ok(regjistrime);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Regjistrim> getRegjistrimById(@PathVariable UUID id) {
        try {
            return regjistrimService.getRegjistrimById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Regjistrim> createRegjistrim(@RequestBody Regjistrim regjistrim) {
        try {
            Regjistrim created = regjistrimService.createRegjistrim(regjistrim);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Regjistrim> updateRegjistrim(
            @PathVariable UUID id, @RequestBody Regjistrim regjistrim) {
        try {
            Regjistrim updated = regjistrimService.updateRegjistrim(id, regjistrim);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegjistrim(@PathVariable UUID id) {
        try {
            regjistrimService.deleteRegjistrim(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
