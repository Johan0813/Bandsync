package BandSync.Controller.Integrantes;

import BandSync.Model.Integrantes.Integrantes;
import BandSync.Model.Login.LoginDTO;
import BandSync.Service.Integrantes.IntegrantesService;
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
@RequestMapping ("api/integrantes")
@CrossOrigin(origins = "*")
public class IntegrantesController {
    @Autowired
    private IntegrantesService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {

        try {

            return ResponseEntity.ok(this.service.findById(id));

        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(this.service.findAll());
    }

  @GetMapping("/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email) {

        try {

            return ResponseEntity.ok(this.service.findByEmail(email));

        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {

        try {

            return ResponseEntity.ok(this.service.findByName(name));

        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<?> findByType(@PathVariable String type) {

        try {

            return ResponseEntity.ok(this.service.findByType(type));

        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/section/{section}")
    public ResponseEntity<?> findBySection(@PathVariable String section) {

        try {

            return ResponseEntity.ok(this.service.findBySection(section));

        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/order")
    public ResponseEntity<?> findAllByOrderByNameAsc() {

        try {

            return ResponseEntity.ok(this.service.findAllByOrderByNameAsc());

        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{email}/{password}")
    public ResponseEntity<?> findByEmailAndPassword(@PathVariable String email, @PathVariable String password) {

        try {

            return ResponseEntity.ok(
                    this.service.findByEmailAndPassword(email, password));

        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {

        try {

            return ResponseEntity.ok(this.service.login(loginDTO.getEmail(), loginDTO.getPassword()
                    )
            );

        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> saveIntegrante (@Validated @RequestBody Integrantes integrante, BindingResult result){
        if (result.hasErrors()) {

            Map<String, String> errors = new HashMap<>();

            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(errors);
        }

        try {

            return ResponseEntity.ok(
                    this.service.saveIntegrante(integrante));

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @PutMapping("/{id}")
    public ResponseEntity<?> editIntegrante(@Validated @RequestBody Integrantes integrante, @PathVariable Integer id, BindingResult result) {

        if (result.hasErrors()) {

            Map<String, String> errors = new HashMap<>();

            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(errors);
        }

        try {

            return ResponseEntity.ok(
                    this.service.editIntegrante(id, integrante));

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIntegrante(@PathVariable Integer id){

        try{

            this.service.deleteIntegrante(id);

            return ResponseEntity.ok(HttpStatus.NO_CONTENT);

        }catch(RuntimeException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}


