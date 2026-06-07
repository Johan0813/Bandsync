package BandSync.Model.Instrumentos;

public class InstrumentosDTO {
    private String name;
    private String condition;
    private Integer quantity;

    public InstrumentosDTO() {
    }

    public InstrumentosDTO(String name, String condition, Integer quantity) {
        this.name = name;
        this.condition = condition;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
