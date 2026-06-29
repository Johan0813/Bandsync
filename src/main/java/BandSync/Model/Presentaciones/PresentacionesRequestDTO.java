package BandSync.Model.Presentaciones;

import java.time.LocalDateTime;

public class PresentacionesRequestDTO {

    private LocalDateTime date;
    private String location;

    public PresentacionesRequestDTO() {
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}