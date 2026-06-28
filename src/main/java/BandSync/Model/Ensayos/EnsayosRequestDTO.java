package BandSync.Model.Ensayos;

import BandSync.Model.Integrantes.Integrantes;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EnsayosRequestDTO {

    private LocalDateTime date;
    private String section;
    private Integrantes integrante;
    private String assistance;

    public EnsayosRequestDTO() {
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
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
        this.assistance = assistance;
    }
}