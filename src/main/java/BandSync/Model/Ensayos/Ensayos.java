package BandSync.Model.Ensayos;

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
    private String asistencia;

    public Ensayos() {
    }

    public Ensayos(LocalDate date, String section, Integrantes integrante, String asistencia) {
        this.date = date;
        this.section = section;
        this.integrante = integrante;
        this.asistencia = asistencia;
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

    public String getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(String asistencia) {
        this.asistencia = asistencia;
    }
}
