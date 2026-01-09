package al.student.controller;

import al.student.model.Lende;
import al.student.service.LendeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/lendet")
@CrossOrigin(origins = "*")
public class LendeController {

    @Autowired
    private LendeService lendeService;

    @GetMapping
    public ResponseEntity<List<Lende>> getAllLende(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer semestri) {
        try {
            List<Lende> lendet;
            if (search != null && !search.trim().isEmpty()) {
                lendet = lendeService.searchLende(search);
            } else if (semestri != null) {
                lendet = lendeService.getLendeBySemestri(semestri);
            } else {
                lendet = lendeService.getAllLende();
            }
            return ResponseEntity.ok(lendet);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lende> getLendeById(@PathVariable UUID id) {
        try {
            return lendeService.getLendeById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Lende> createLende(@RequestBody Lende lende) {
        try {
            Lende created = lendeService.createLende(lende);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lende> updateLende(
            @PathVariable UUID id, @RequestBody Lende lende) {
        try {
            Lende updated = lendeService.updateLende(id, lende);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLende(@PathVariable UUID id) {
        try {
            lendeService.deleteLende(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/aktiv")
    public ResponseEntity<List<Lende>> getActiveLende() {
        try {
            List<Lende> lendet = lendeService.getActiveLende();
            return ResponseEntity.ok(lendet);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
