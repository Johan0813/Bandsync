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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping ("/api/instrumentos")
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

}
