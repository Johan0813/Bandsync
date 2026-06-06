package BandSync.Model.Instrumentos;


import jakarta.persistence.*;

@Entity
@Table(name = "tb_Instrumentos")
public class Instrumentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id", nullable = false)
    private Integer id;
    @Column(name = "nombre", nullable = false, length = 50)
    private String name;
    @Column(name = "condicion", nullable = false, length = 50)
    private String condition;
    @Column(name = "disponibilidad", nullable = false, length = 50)
    private String availability;

    public Instrumentos() {
    }

    public Instrumentos(String name, String condition, String availability) {
        this.name = name;
        this.condition = condition;
        this.availability = availability;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}