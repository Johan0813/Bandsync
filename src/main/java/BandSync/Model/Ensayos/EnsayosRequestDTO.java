package BandSync.Model.Ensayos;

import java.time.LocalDateTime;

public class EnsayosRequestDTO {

    private LocalDateTime date;
    private String section;

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
}