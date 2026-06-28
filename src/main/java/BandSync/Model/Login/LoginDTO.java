package BandSync.Model.Login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class LoginDTO {

    @NotBlank(message = "El correo es obligatorio")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@(bco\\.or\\.cr|int\\.bco\\.or\\.cr)$",
            message = "El correo debe terminar en @bco.or.cr o @int.bco.or.cr"
    )
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    public LoginDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}