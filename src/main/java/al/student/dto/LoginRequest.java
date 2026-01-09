package al.student.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Email është i detyrueshëm")
    @Email(message = "Email duhet të jetë i vlefshëm")
    private String email;

    @NotBlank(message = "Fjalëkalimi është i detyrueshëm")
    private String fjalekalimi;
}
