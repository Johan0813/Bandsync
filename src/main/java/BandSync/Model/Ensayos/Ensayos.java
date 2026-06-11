package BandSync.Model.Ensayos;

import BandSync.Model.Integrantes.Integrantes;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table (name = "tb_Ensayos")
public class Ensayos {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "fecha", nullable = false)
    private LocalDate date;
    @Column(name = "seccion", nullable = false)
    private String section;
    @ManyToOne
    @JoinColumn(name = "id_integrante", nullable = false, foreignKey = @ForeignKey(name = "fk_ensayo_integrante"))
    private Integrantes integrante;
    @Column(name = "asistencia", nullable = false, length = 20)
    private String assistance;

    public Ensayos() {
    }

    public Ensayos(LocalDate date, String section, Integrantes integrante, String assistance) {
        this.date = date;
        this.section = section;
        this.integrante = integrante;
        this.assistance = this.assistance;
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

    public Integrantes getIntegrante() {
        return integrante;
    }

    public void setIntegrante(Integrantes integrante) {
        this.integrante = integrante;
    }

    public String getAssistance() {
        return assistance;
    }

    public void setAssistance(String assistance) {
        this.assistance = assistance;
    }
}
