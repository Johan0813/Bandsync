package BandSync.Model.Presentaciones;

import BandSync.Model.Integrantes.Integrantes;
import jakarta.persistence.*;

import java.time.LocalDate;

    @Entity
    @Table(name = "tb_presentaciones")
    public class Presentaciones {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        private Integer id;

        @Column(name = "fecha", nullable = false)
        private LocalDate date;

        @Column(name = "ubicacion", nullable = false, length = 100)
        private String location;

        @ManyToOne
        @JoinColumn(name = "id_integrante", nullable = false, foreignKey = @ForeignKey(name = "fk_presentacion_integrante"))
        private Integrantes integrante;

        @Column(name = "asistencia", nullable = false, length = 20)
        private String assistance;

        public Presentaciones() {
        }

        public Presentaciones(LocalDate date, String location, Integrantes integrante, String assistance) {
            this.date = date;
            this.location = location;
            this.integrante = integrante;
            this.assistance = assistance;
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

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
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


