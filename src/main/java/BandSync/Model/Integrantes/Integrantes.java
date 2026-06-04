package BandSync.Model.Integrantes;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_Integrantes")
public class Integrantes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String email;
    private Integer age;
    private String type;
    private String instrument;
    private String section;

    public Integrantes() {
    }

    public Integrantes(String name, String email, Integer age, String type, String instrument, String section) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.type = type;
        this.instrument = instrument;
        this.section = section;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
