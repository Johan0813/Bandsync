package BandSync.Model.Ensayos;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table (name = "tb_Ensayos")
public class Ensayos {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)

    private Integer id;
    private LocalDate date;
    private String section;

    public Ensayos() {
    }

    public Ensayos(Integer id, LocalDate date, String section) {
        this.id = id;
        this.date = date;
        this.section = section;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
