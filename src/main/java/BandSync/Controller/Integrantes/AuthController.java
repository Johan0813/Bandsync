package BandSync.Controller.Integrantes;

import BandSync.Model.Login.LoginDTO;
import BandSync.Service.Integrantes.AuthService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/integrantes/login")
@CrossOrigin(origins = "*")

public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginDTO dto){

        try{

            return ResponseEntity.ok(this.authService.login(dto)
            );

        }catch(RuntimeException e){

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}