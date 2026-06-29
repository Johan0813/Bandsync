package BandSync.Controller.Presentaciones;

import BandSync.Model.Presentaciones.PresentacionesRequestDTO;
import BandSync.Service.Presentaciones.PresentacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("api/presentaciones")
@CrossOrigin(origins = "*")
public class PresentacionesController {
    @Autowired
    PresentacionesService presentacionesService;


    @PostMapping
    public ResponseEntity<?> savePresentation(@RequestBody PresentacionesRequestDTO presentacion){

        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(this.presentacionesService.savePresentation(presentacion));

        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(
                this.presentacionesService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        try{
            return ResponseEntity.ok(this.presentacionesService.findById(id)
            );
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<?> findByDate(@PathVariable LocalDateTime date){
        try{
            return ResponseEntity.ok(this.presentacionesService.findByDate(date)
            );
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<?> findByLocation(@PathVariable String location){
        try{
            return ResponseEntity.ok(this.presentacionesService.findByLocation(location));
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping ("/assistance/{assistance}")

    public ResponseEntity<?> findByAssistance (@PathVariable String assistance){
        try {
            return ResponseEntity.ok(this.presentacionesService.findByAssistance(assistance));
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePresentation(@PathVariable Integer id){
        try{
            this.presentacionesService.deletePresentation(id);
            return ResponseEntity.ok("Presentacion eliminada correctamente"
            );
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editPresentation(@PathVariable Integer id, @RequestBody PresentacionesRequestDTO presentacion){
        try{
            return ResponseEntity.ok(
                    this.presentacionesService.editPresentation(id, presentacion)
            );

        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/assistance/{assistance}")
    public ResponseEntity<?> cambiarAsistencia(@PathVariable Integer id, @PathVariable String estado){
        try{
            return ResponseEntity.ok(this.presentacionesService.cambiarAsistencia(id, estado));
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}//fin de la clase
