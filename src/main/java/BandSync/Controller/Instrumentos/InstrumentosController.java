package BandSync.Controller.Instrumentos;

import BandSync.Model.Instrumentos.Instrumentos;
import BandSync.Model.Instrumentos.InstrumentosDTO;
import BandSync.Service.Instrumentos.InstrumentosService;
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
@RequestMapping ("/api/instrumentos")
@CrossOrigin(origins = "*")
public class InstrumentosController {

    @Autowired
    private InstrumentosService service;

    @PostMapping
    public ResponseEntity<?> saveInstrumento(@Validated @RequestBody Instrumentos instrumento, BindingResult result){

        if(result.hasErrors()){

            Map<String, String> errors = new HashMap<>();

            for(FieldError error : result.getFieldErrors()){
                errors.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(errors);
        }

        try{

            InstrumentosDTO dto = this.service.saveInstrumento(instrumento);

            return ResponseEntity.status(HttpStatus.CREATED).body(dto);

        }catch(RuntimeException e){

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(this.service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id){

        try{
            return ResponseEntity.ok(this.service.findById(id));

        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name){

        try{
            return ResponseEntity.ok(this.service.findByName(name));

        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> editInstrumento(
            @Validated @RequestBody Instrumentos instrumento,
            BindingResult result,
            @PathVariable Integer id){

        if(result.hasErrors()){

            Map<String, String> errors = new HashMap<>();

            for(FieldError error : result.getFieldErrors()){
                errors.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(errors);
        }

        try{

            return ResponseEntity.ok(this.service.editInstrumento(id, instrumento));

        }catch(RuntimeException e){

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/{quantity}")
    public ResponseEntity<?> deleteInstrumento(@PathVariable Integer id, @PathVariable Integer quantity){

        try{

            return ResponseEntity.ok(this.service.deleteInstrumento(id, quantity));

        }catch(RuntimeException e){

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
