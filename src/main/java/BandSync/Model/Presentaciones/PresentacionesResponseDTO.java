package BandSync.Model.Presentaciones;

import java.time.LocalDate;

public class PresentacionesResponseDTO {

    private Integer id;
    private LocalDate date;
    private String location;
    private String integrante;
    private Integer integranteId;
    private String asistencia;

    public PresentacionesResponseDTO() {
    }

    public PresentacionesResponseDTO(Integer id, LocalDate date, String location, String integrante, Integer integranteId, String asistencia) {
        this.id = id;
        this.date = date;
        this.location = location;
        this.integrante = integrante;
        this.integranteId = integranteId;
        this.asistencia = asistencia;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getDate() {
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

    public String getAsistencia() {
        return asistencia;
    }
}