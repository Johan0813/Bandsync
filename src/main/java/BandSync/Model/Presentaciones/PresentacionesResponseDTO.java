package BandSync.Model.Presentaciones;

import java.time.LocalDateTime;

public class PresentacionesResponseDTO {

    private Integer id;
    private LocalDateTime date;
    private String location;
    private String integrante;
    private Integer integranteId;
    private String asisstance;

    public PresentacionesResponseDTO() {
    }

    public PresentacionesResponseDTO(Integer id, LocalDateTime date, String location, String integrante, Integer integranteId, String assistance) {
        this.id = id;
        this.date = date;
        this.location = location;
        this.integrante = integrante;
        this.integranteId = integranteId;
        this.asisstance = assistance;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getIntegrante() {
        return integrante;
    }

    public Integer getIntegranteId() {
        return integranteId;
    }

    public String getAsisstance() {
        return asisstance;
    }
}