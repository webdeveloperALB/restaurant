package al.student.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Regjistrim {
    private UUID id;
    private UUID studentId;
    private UUID lendeId;
    private String vitiAkademik;
    private int semestri;
    private LocalDateTime dataRegjistrimit;
    private boolean aktiv;
    private LocalDateTime krijuarMe;

    private Student student;
    private Lende lende;

    public Regjistrim() {
    }

    public Regjistrim(UUID id, UUID studentId, UUID lendeId, String vitiAkademik,
                      int semestri, LocalDateTime dataRegjistrimit, boolean aktiv,
                      LocalDateTime krijuarMe) {
        this.id = id;
        this.studentId = studentId;
        this.lendeId = lendeId;
        this.vitiAkademik = vitiAkademik;
        this.semestri = semestri;
        this.dataRegjistrimit = dataRegjistrimit;
        this.aktiv = aktiv;
        this.krijuarMe = krijuarMe;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
    }

    public UUID getLendeId() {
        return lendeId;
    }

    public void setLendeId(UUID lendeId) {
        this.lendeId = lendeId;
    }

    public String getVitiAkademik() {
        return vitiAkademik;
    }

    public void setVitiAkademik(String vitiAkademik) {
        this.vitiAkademik = vitiAkademik;
    }

    public int getSemestri() {
        return semestri;
    }

    public void setSemestri(int semestri) {
        this.semestri = semestri;
    }

    public LocalDateTime getDataRegjistrimit() {
        return dataRegjistrimit;
    }

    public void setDataRegjistrimit(LocalDateTime dataRegjistrimit) {
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Lende getLende() {
        return lende;
    }

    public void setLende(Lende lende) {
        this.lende = lende;
    }
}
