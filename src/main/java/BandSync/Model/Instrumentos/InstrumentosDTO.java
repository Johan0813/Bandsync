package BandSync.Model.Instrumentos;

public class InstrumentosDTO {
    private String type;
    private String condition;
    private String availability;

    public InstrumentosDTO() {
    }

    public InstrumentosDTO(String type, String condition, String availability) {
        this.type = type;
        this.condition = condition;
        this.availability = availability;
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
