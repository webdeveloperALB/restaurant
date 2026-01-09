package al.student.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Nota {
    private UUID id;
    private UUID regjistrimId;
    private BigDecimal nota;
    private String llojiProvimit;
    private LocalDate dataProvimit;
    private String komente;
    private LocalDateTime krijuarMe;
    private LocalDateTime perditsuarMe;

    private Regjistrim regjistrim;

    public Nota() {
    }

    public Nota(UUID id, UUID regjistrimId, BigDecimal nota, String llojiProvimit,
                LocalDate dataProvimit, String komente, LocalDateTime krijuarMe,
                LocalDateTime perditsuarMe) {
        this.id = id;
        this.regjistrimId = regjistrimId;
        this.nota = nota;
        this.llojiProvimit = llojiProvimit;
        this.dataProvimit = dataProvimit;
        this.komente = komente;
        this.krijuarMe = krijuarMe;
        this.perditsuarMe = perditsuarMe;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getRegjistrimId() {
        return regjistrimId;
    }

    public void setRegjistrimId(UUID regjistrimId) {
        this.regjistrimId = regjistrimId;
    }

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }

    public String getLlojiProvimit() {
        return llojiProvimit;
    }

    public void setLlojiProvimit(String llojiProvimit) {
        this.llojiProvimit = llojiProvimit;
    }

    public LocalDate getDataProvimit() {
        return dataProvimit;
    }

    public void setDataProvimit(LocalDate dataProvimit) {
        this.dataProvimit = dataProvimit;
    }

    public String getKomente() {
        return komente;
    }

    public void setKomente(String komente) {
        this.komente = komente;
    }

    public LocalDateTime getKrijuarMe() {
        return krijuarMe;
    }

    public void setKrijuarMe(LocalDateTime krijuarMe) {
        this.krijuarMe = krijuarMe;
    }

    public LocalDateTime getPerditsuarMe() {
        return perditsuarMe;
    }

    public void setPerditsuarMe(LocalDateTime perditsuarMe) {
        this.perditsuarMe = perditsuarMe;
    }

    public Regjistrim getRegjistrim() {
        return regjistrim;
    }

    public void setRegjistrim(Regjistrim regjistrim) {
        this.regjistrim = regjistrim;
    }
}
