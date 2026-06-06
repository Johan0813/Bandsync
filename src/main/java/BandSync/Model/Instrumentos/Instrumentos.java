package BandSync.Model.Instrumentos;


import jakarta.persistence.*;

@Entity
@Table(name = "tb_Instrumentos")
public class Instrumentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String type;
    private String condition;
    private String availability;

    public Instrumentos() {
    }

    public Instrumentos(String type, String condition, String availability) {
        this.type = type;
        this.condition = condition;
        this.availability = availability;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
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