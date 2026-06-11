package BandSync.Model.Ensayos;

import BandSync.Model.Integrantes.Integrantes;

import java.time.LocalDate;

public class EnsayosDTO {

    private LocalDate date;
    private String section;
    private Integrantes integrante;
    private String assistance;

    public EnsayosDTO() {
    }

    public EnsayosDTO(LocalDate date, String section, Integrantes integrante, String assistance) {
        this.date = date;
        this.section = section;
        this.integrante = integrante;
        this.assistance = assistance;
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

    public String getAssistance() {
        return assistance;
    }

    public void setAssistance(String assistance) {
        this.assistance = this.assistance;
    }
}