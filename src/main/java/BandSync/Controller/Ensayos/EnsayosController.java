package BandSync.Controller.Ensayos;

import BandSync.Model.Ensayos.Ensayos;
import BandSync.Service.Ensayos.EnsayosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RequestMapping
@RestController("api/ensayos")
@CrossOrigin(origins = "*")
public class EnsayosController {
@Autowired
    EnsayosService ensayosService;
//para guardar
    @PostMapping("save")
    public ResponseEntity<?> saveEnsayo (@Validated @RequestBody Ensayos ensayos, BindingResult result){
        if (result.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()){
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return  ResponseEntity.badRequest().body(errors);
        }
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(this.ensayosService.saveEnsayo(ensayos));
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("all")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(
                this.ensayosService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        try{
            return ResponseEntity.ok(this.ensayosService.findById(id));
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<?> findByDate(@PathVariable LocalDate date){
        try{
            return ResponseEntity.ok(this.ensayosService.findByDate(date));
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @GetMapping("/section/{section}")
    public ResponseEntity<?> findBySection(@PathVariable String section) {
        try {
            return ResponseEntity.ok(this.ensayosService.findBySection(section));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/asistencia/{asistencia}")
            public ResponseEntity<?> findByAsistencia(@PathVariable String asistencia){
            try {
                return ResponseEntity.ok(this.ensayosService.findByAssistance(asistencia));
            }catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        }


        @DeleteMapping("/{id}")
        public ResponseEntity<?> deleteEnsayos(@PathVariable Integer id){
        try {
            this.ensayosService.deleteEnsayo(id);
            return ResponseEntity.ok("El ensayo fue eliminado correctamente");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
          }
        }
    @PutMapping("/{id}")
    public ResponseEntity<?> editEnsayo(@PathVariable Integer id, @Validated @RequestBody Ensayos ensayo, BindingResult result){
        if (result.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()){
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            return ResponseEntity.ok(
                    this.ensayosService.editEnsayo(id, ensayo)
            );
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}// Fin clase
