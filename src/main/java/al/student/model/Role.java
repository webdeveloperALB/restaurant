package al.student.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private UUID id;
    private String emri;
    private String pershkrimi;
    private Integer nivelAksesi;
    private LocalDateTime krijuarMe;
}
