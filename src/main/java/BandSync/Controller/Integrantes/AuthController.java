package BandSync.Controller.Integrantes;

import BandSync.Model.Login.LoginDTO;
import BandSync.Service.Integrantes.AuthService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/auth/login")
@CrossOrigin(origins = "*")

public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<?> login(@Validated @RequestBody LoginDTO dto, BindingResult result){
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();

            for (FieldError error : result.getFieldErrors()) {
                errores.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(errores);
        }
        try{

            return ResponseEntity.ok(this.authService.login(dto)
            );

        }catch(RuntimeException e){

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}