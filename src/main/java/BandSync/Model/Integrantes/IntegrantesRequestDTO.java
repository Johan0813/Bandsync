    package BandSync.Model.Integrantes;

    public class IntegrantesRequestDTO {
        private String name;
        private String email;
        private Integer age;
        private String type;
        private String instrument;
        private Integer instrumentId;
        private String section;
        private String password;

        public IntegrantesRequestDTO() {
        }

        public IntegrantesRequestDTO(String name, String email, Integer age, String type, String instrument, Integer instrumentId,  String section, String password) {
            this.name = name;
            this.email = email;
            this.age = age;
            this.type = type;
            this.instrument = instrument;
            this.instrumentId = instrumentId;
            this.section = section;
            this.password = password;
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

        public Integer getInstrumentId() {
            return instrumentId;
        }

        public void setInstrumentId(Integer instrumentId) {
            this.instrumentId = instrumentId;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
