package BandSync.Model.Ensayos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EnsayosResponseDTO {

    private Integer id;
    private LocalDateTime date;
    private String section;
    private String integrante;
    private Integer integranteId;
    private String assistance;

    public EnsayosResponseDTO() {
    }

    public EnsayosResponseDTO(Integer id, LocalDateTime date, String section, String integrante, Integer integranteId, String assistance) {
        this.id = id;
        this.date = date;
        this.section = section;
        this.integrante = integrante;
        this.integranteId = integranteId;
        this.assistance = assistance;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getSection() {
        return section;
    }

    public String getIntegrante() {
        return integrante;
    }

    public Integer getIntegranteId() {
        return integranteId;
    }

    public String getAssistance() {
        return assistance;
    }
}