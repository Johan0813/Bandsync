package BandSync.Model;

import java.time.LocalDate;

public class PresentacionesDTO {
    private LocalDate date;
    private String location;
    private String integrante;
    private String asistencia;

    public PresentacionesDTO() {
    }

    public PresentacionesDTO(LocalDate date, String location, String integrante, String asistencia) {
        this.date = date;
        this.location = location;
        this.integrante = integrante;
        this.asistencia = asistencia;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIntegrante() {
        return integrante;
    }

    public void setIntegrante(String integrante) {
        this.integrante = integrante;
    }

    public String getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(String asistencia) {
        this.asistencia = asistencia;
    }
}

