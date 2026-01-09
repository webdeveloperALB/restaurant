package al.student.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Email është i detyrueshëm")
    @Email(message = "Email duhet të jetë i vlefshëm")
    private String email;

    @NotBlank(message = "Fjalëkalimi është i detyrueshëm")
    @Size(min = 8, message = "Fjalëkalimi duhet të ketë të paktën 8 karaktere")
    private String fjalekalimi;

    @NotBlank(message = "Emri është i detyrueshëm")
    private String emri;

    @NotBlank(message = "Mbiemri është i detyrueshëm")
    private String mbiemri;

    private String telefoni;
    private String adresa;
    private LocalDate dataLindjes;

    private String roli = "student";
}
