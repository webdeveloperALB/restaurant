package al.student.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Lende {
    private UUID id;
    private String emri;
    private String kodi;
    private String pershkrimi;
    private int kredite;
    private int semestri;
    private boolean aktiv;
    private LocalDateTime krijuarMe;
    private LocalDateTime perditsuarMe;

    public Lende() {
    }

    public Lende(UUID id, String emri, String kodi, String pershkrimi, int kredite,
                 int semestri, boolean aktiv, LocalDateTime krijuarMe, LocalDateTime perditsuarMe) {
        this.id = id;
        this.emri = emri;
        this.kodi = kodi;
        this.pershkrimi = pershkrimi;
        this.kredite = kredite;
        this.semestri = semestri;
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

    public String getKodi() {
        return kodi;
    }

    public void setKodi(String kodi) {
        this.kodi = kodi;
    }

    public String getPershkrimi() {
        return pershkrimi;
    }

    public void setPershkrimi(String pershkrimi) {
        this.pershkrimi = pershkrimi;
    }

    public int getKredite() {
        return kredite;
    }

    public void setKredite(int kredite) {
        this.kredite = kredite;
    }

    public int getSemestri() {
        return semestri;
    }

    public void setSemestri(int semestri) {
        this.semestri = semestri;
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
}
