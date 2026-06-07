package BandSync.Model.Instrumentos;

public class InstrumentosDTO {
    private String name;
    private String condition;
    private String availability;
    private Integer quantity;

    public InstrumentosDTO() {
    }

    public InstrumentosDTO(String name, String condition, String availability, Integer quantity ) {
        this.name = name;
        this.condition = condition;
        this.availability = availability;
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

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
