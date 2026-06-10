package BandSync.Model.Ensayos;

import BandSync.Model.Integrantes.Integrantes;

import java.time.LocalDate;

public class EnsayosDTO {

    private LocalDate date;
    private String section;
    private Integrantes integrante;
    private String asistencia;

    public EnsayosDTO() {
    }

    public EnsayosDTO(LocalDate date, String section, Integrantes integrante, String asistencia) {
        this.date = date;
        this.section = section;
        this.integrante = integrante;
        this.asistencia = asistencia;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Integrantes getIntegrante() {
        return integrante;
    }

    public void setIntegrante(Integrantes integrante) {this.integrante = integrante;}

    public String getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(String asistencia) {
        this.asistencia = asistencia;
    }
}