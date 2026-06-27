package BandSync.Model.Instrumentos;

public class InstrumentosRequestDTO {
    private String name;
    private Integer quantity;

    public InstrumentosRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
