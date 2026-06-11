package BandSync.Service.Ensayos;

import BandSync.Model.Ensayos.Ensayos;
import BandSync.Model.Ensayos.EnsayosDTO;
import BandSync.Model.Integrantes.Integrantes;
import BandSync.Model.Presentaciones.Presentaciones;
import BandSync.Repository.Ensayos.EnsayosRepository;
import BandSync.Repository.Instrumentos.InstrumentosRepository;
import BandSync.Repository.Integrantes.IntegrantesRepository;
import BandSync.Repository.Presentaciones.PresentacionesRepository;
import BandSync.Service.Presentaciones.PresentacionesService;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

@Service
public class EnsayosService {
//inyección de dependencias
    @Autowired
    EnsayosRepository ensayosRepository;
    @Autowired
    IntegrantesRepository integrantesRepository;
    //metodos
    public List<EnsayosDTO> convertirListEnsayosDTO (List<Ensayos> ensayosList){
        List<EnsayosDTO> listDTO= new ArrayList<>();
        for (Ensayos ensayos: ensayosList){
            listDTO.add(this.convertirEnsayosDTO(ensayos));
        }
        return listDTO;
    }

    public EnsayosDTO convertirEnsayosDTO(Ensayos ensayos ){
        EnsayosDTO dto = new EnsayosDTO();
        dto.setDate(ensayos.getDate());
        dto.setAsistencia(ensayos.getAsistencia());
        dto.setIntegrante(ensayos.getIntegrante());
        dto.setSection(ensayos.getSection());
        return dto;
    }

    public List<EnsayosDTO> findByDate(LocalDate date){
        List<Ensayos> ensayos = this.ensayosRepository.findByDate(date);
        if (ensayos.isEmpty()){
            throw  new RuntimeException("No existen ensayos en la fecha indicada");
        }
        return this.convertirListEnsayosDTO(ensayos);
    }

    public List<EnsayosDTO> findBySection(String section){
        List<Ensayos> ensayos = this.ensayosRepository.findBySection(section);
        if (ensayos.isEmpty()){
            throw new RuntimeException("La sección que indicaste no esta disponible");
        }
        return this.convertirListEnsayosDTO(ensayos);
    }

    public List<EnsayosDTO> findByAssistance (String assistance){
        List<Ensayos> ensayos = this.ensayosRepository.findByAssistance(assistance);
        if (ensayos.isEmpty()){
            throw new RuntimeException("No hay lista de asistencia en este momento");
        }
        return this.convertirListEnsayosDTO(ensayos);
    }

    public List<EnsayosDTO> findAll(){
        return this.convertirListEnsayosDTO(this.ensayosRepository.findAll());
    }

//eliminar un ensayo
    public void deleteEnsayo (Integer id){
        Optional<Ensayos> optional = this.ensayosRepository.findById(id);
        if (optional.isEmpty()){
            throw new RuntimeException("El Ensayo no existe");
        }
        LocalDate date = optional.get().getDate();
        List<Ensayos> ensayos = this.ensayosRepository.findByDate(date);

        for (Ensayos ensayo : ensayos){
            this.ensayosRepository.delete(ensayo);
        }
    }
//guardar ensayos
    public List<EnsayosDTO> saveEnsayo (Ensayos ensayos){
        if (!this.ensayosRepository.findByDate(ensayos.getDate()).isEmpty()){
            throw new RuntimeException("Ya existe un ensayo para esta fecha!");
        }
        List<Integrantes> integrantes = this.integrantesRepository.findAll();
        List<EnsayosDTO> ensayosCreados = new ArrayList<>();

        //Por cada integrante de tipo Integrantes dentro de integrantes
        for (Integrantes integrante : integrantes){
            Ensayos nuevo = new Ensayos();
            nuevo.setDate(ensayos.getDate());
            nuevo.setAsistencia(ensayos.getAsistencia());
            nuevo.setSection(ensayos.getSection());
            nuevo.setIntegrante(integrante);
            Ensayos agendado = this.ensayosRepository.save(nuevo);

            ensayosCreados.add(this.convertirEnsayosDTO(agendado));
        }
        return ensayosCreados;
    }
 //editar ensayos
 public List<EnsayosDTO> editEnsayo(Integer id, Ensayos ensayosEdit){
        Optional<Ensayos> optional = this.ensayosRepository.findById(id);
        if (optional.isEmpty()) {
            throw  new RuntimeException("El ensayo no existe");
        }
            LocalDate fechaYaEstablecida = optional.get().getDate();

        if (!fechaYaEstablecida.equals(ensayosEdit.getDate()) && !this.ensayosRepository.findByDate(ensayosEdit.getDate()).isEmpty()){
            throw new RuntimeException("Ya existe un ensayo en esta fecha!");
        }
        List<Ensayos> ensayos = this.ensayosRepository.findByDate(fechaYaEstablecida);
        List<EnsayosDTO> editados = new ArrayList<>();

        for (Ensayos ensayo : ensayos){
            ensayo.setDate(ensayosEdit.getDate());
            ensayo.setSection(ensayosEdit.getSection());
            ensayo.setAsistencia(ensayosEdit.getAsistencia());
            Ensayos agendado = this.ensayosRepository.save(ensayo);
            editados.add(this.convertirEnsayosDTO(agendado));
        }
        return editados;
 }

 public Ensayos findById(Integer id){
        Optional<Ensayos> ensayo = this.ensayosRepository.findById(id);
        if (ensayo.isEmpty()){
            throw new RuntimeException("El ensayo no existe!");
        }
        return ensayo.get();
 }

}//Fin de la clase
