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
    @Column (name = "cantidad", nullable = false)
    private Integer quantity;

    public Instrumentos() {
    }

    public Instrumentos(String name, String condition, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
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

    public void setName(String name){
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}