package al.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String type = "Bearer";
    private UUID userId;
    private String email;
    private String emri;
    private String mbiemri;
    private String roli;
    private String mesazh;

    public AuthResponse(String token, UUID userId, String email, String emri, String mbiemri, String roli) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.emri = emri;
        this.mbiemri = mbiemri;
        this.roli = roli;
    }

    public AuthResponse(String mesazh) {
        this.mesazh = mesazh;
    }
}
