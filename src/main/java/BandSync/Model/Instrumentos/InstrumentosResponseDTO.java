package BandSync.Model.Instrumentos;

public class InstrumentosResponseDTO {
    private Integer id;
    private String name;
    private Integer quantity;

    public InstrumentosResponseDTO() {
    }

    public InstrumentosResponseDTO(Integer id, String name, Integer quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
