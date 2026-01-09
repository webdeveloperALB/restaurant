package al.student.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Student {
    private UUID id;
    private String emri;
    private String mbiemri;
    private String email;
    private LocalDate dataLindjes;
    private String adresa;
    private String telefoni;
    private LocalDate dataRegjistrimit;
    private boolean aktiv;
    private LocalDateTime krijuarMe;
    private LocalDateTime perditsuarMe;

    public Student() {
    }

    public Student(UUID id, String emri, String mbiemri, String email, LocalDate dataLindjes,
                   String adresa, String telefoni, LocalDate dataRegjistrimit, boolean aktiv,
                   LocalDateTime krijuarMe, LocalDateTime perditsuarMe) {
        this.id = id;
        this.emri = emri;
        this.mbiemri = mbiemri;
        this.email = email;
        this.dataLindjes = dataLindjes;
        this.adresa = adresa;
        this.telefoni = telefoni;
        this.dataRegjistrimit = dataRegjistrimit;
        this.aktiv = aktiv;
        this.krijuarMe = krijuarMe;
        this.perditsuarMe = perditsuarMe;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmri() {
        return emri;
    }

    public void setEmri(String emri) {
        this.emri = emri;
    }

    public String getMbiemri() {
        return mbiemri;
    }

    public void setMbiemri(String mbiemri) {
        this.mbiemri = mbiemri;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataLindjes() {
        return dataLindjes;
    }

    public void setDataLindjes(LocalDate dataLindjes) {
        this.dataLindjes = dataLindjes;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefoni() {
        return telefoni;
    }

    public void setTelefoni(String telefoni) {
        this.telefoni = telefoni;
    }

    public LocalDate getDataRegjistrimit() {
        return dataRegjistrimit;
    }

    public void setDataRegjistrimit(LocalDate dataRegjistrimit) {
        this.dataRegjistrimit = dataRegjistrimit;
    }

    public boolean isAktiv() {
        return aktiv;
    }

    public void setAktiv(boolean aktiv) {
        this.aktiv = aktiv;
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

    public String getEmriPlote() {
        return emri + " " + mbiemri;
    }
}
