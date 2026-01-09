package al.student.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Perdoruesi {
    private UUID id;
    private String email;
    private String fjalekalimiHash;
    private UUID roliId;
    private String emri;
    private String mbiemri;
    private String fotoProfili;
    private String telefoni;
    private String adresa;
    private LocalDate dataLindjes;
    private Boolean aktiv;
    private Boolean emailVerifikuar;
    private LocalDateTime lastLogin;
    private LocalDateTime krijuarMe;
    private LocalDateTime perditsuarMe;

    private Role role;
}
