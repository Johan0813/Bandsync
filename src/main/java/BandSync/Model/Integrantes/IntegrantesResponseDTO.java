package BandSync.Model.Integrantes;

public class IntegrantesResponseDTO {

    private Integer id;
    private String name;
    private String email;
    private Integer age;
    private String type;
    private String instrument;
    private Integer instrumentId;
    private String section;

    public IntegrantesResponseDTO() {
    }

    public IntegrantesResponseDTO(Integer id, String name, String email, Integer age, String type, String instrument, Integer instrumentId, String section) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.type = type;
        this.instrument = instrument;
        this.instrumentId = instrumentId;
        this.section = section;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Integer getAge() {
        return age;
    }

    public String getType() {
        return type;
    }

    public String getInstrument() {
        return instrument;
    }

    public Integer getInstrumentId() {
        return instrumentId;
    }

    public String getSection() {
        return section;
    }
}

