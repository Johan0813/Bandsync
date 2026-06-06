package BandSync.Model.Integrantes;
import BandSync.Model.Instrumentos.Instrumentos;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

@Entity
@Table(name = "tb_Integrantes")
public class Integrantes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id", nullable = false)
    private Integer id;
    @Column (name = "nombre", nullable = false, length = 50)
    private String name;
    @Column (name = "correo_electronico", nullable = false, length = 75)
    private String email;
    @Min(15)
    @Max(65)
    @Column (name = "edad", nullable = false)
    private Integer age;
    @Column (name = "rol", nullable = false, length = 25)
    private String type;
    @OneToOne
    @JoinColumn(name = "id_instrumento", nullable = false)
    private Instrumentos instrument;
    @Column (name = "seccion", nullable = false, length = 30)
    private String section;

    public Integrantes() {
    }

    public Integrantes(String name, String email, Integer age, String type, Instrumentos instrument, String section) {
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

    public Instrumentos getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrumentos instrument) {
        this.instrument = instrument;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
