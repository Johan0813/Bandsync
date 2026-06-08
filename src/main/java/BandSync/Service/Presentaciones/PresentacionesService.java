package BandSync.Service.Presentaciones;

import BandSync.Model.Integrantes.Integrantes;
import BandSync.Model.Presentaciones.Presentaciones;
import BandSync.Model.Presentaciones.PresentacionesDTO;
import BandSync.Repository.Ensayos.EnsayosRepository;
import BandSync.Repository.Instrumentos.InstrumentosRepository;
import BandSync.Repository.Integrantes.IntegrantesRepository;
import BandSync.Repository.Presentaciones.PresentacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PresentacionesService {

    //inyecciones
    @Autowired
    PresentacionesRepository presentacionesRepository;
    @Autowired
    IntegrantesRepository integrantesRepository;

    //Convertis
    public  List<PresentacionesDTO> convertirListPresentacionesDTO (List<Presentaciones> presentacionesList){
        List<PresentacionesDTO> listDTO= new ArrayList<>();
        for (Presentaciones presentaciones: presentacionesList){
            listDTO.add(this.convertirPresentacionesDTO(presentaciones));
        }
        return listDTO;
    }

    public PresentacionesDTO convertirPresentacionesDTO (Presentaciones presentaciones){
        PresentacionesDTO dto= new PresentacionesDTO();
        dto.setDate(presentaciones.getDate());
        dto.setLocation(presentaciones.getLocation());
        dto.setAsistencia(presentaciones.getAssistance());
        return dto;
    }


    public List<PresentacionesDTO> findByDate(LocalDate date){
        return this.convertirListPresentacionesDTO(this.presentacionesRepository.findByDate(date));
    }

    public List<PresentacionesDTO> findByLocation(String location){
        return this.convertirListPresentacionesDTO(this.presentacionesRepository.findByLocation(location));
    }

    public List<PresentacionesDTO> findAll(){
        return this.convertirListPresentacionesDTO(presentacionesRepository.findAll());
    }

    public void deletePresentation (Integer id){
        Optional<Presentaciones> optional = this.presentacionesRepository.findById(id);

        if (optional.isPresent()){
            LocalDate date = optional.get().getDate();

            List<Presentaciones> presentaciones = this.presentacionesRepository.findByDate(date);

            for (Presentaciones presentacion : presentaciones){
                this.presentacionesRepository.delete(presentacion);
            }
        }
    }

    public List <PresentacionesDTO> savePresentation (Presentaciones presentaciones){
        if(!this.presentacionesRepository
                .findByDate(presentaciones.getDate())
                .isEmpty()){

            return null;
        }
    List<Integrantes> integrantes = this.integrantesRepository.findAll();
    List<PresentacionesDTO> presentacionesCreadas = new ArrayList<>();

    for (Integrantes integrante : integrantes){
        Presentaciones nueva = new Presentaciones();
        nueva.setDate(presentaciones.getDate());
        nueva.setLocation(presentaciones.getLocation());
        nueva.setIntegrante(integrante);
        nueva.setAssistance(presentaciones.getAssistance()
        );
        Presentaciones guardada = this.presentacionesRepository.save(nueva);

        presentacionesCreadas.add(this.convertirPresentacionesDTO(guardada)
        );
    }
    return presentacionesCreadas;
    }


    public List<PresentacionesDTO> editPresentation(Integer id, Presentaciones presentacionesEdit){

        Optional<Presentaciones> optional = this.presentacionesRepository.findById(id);
        if(optional.isPresent()){
            LocalDate fechaOriginal = optional.get().getDate();

            if(!fechaOriginal.equals(presentacionesEdit.getDate()) && !this.presentacionesRepository.findByDate(presentacionesEdit.getDate()).isEmpty()){
                return null;
            }
            List<Presentaciones> presentaciones = this.presentacionesRepository.findByDate(fechaOriginal);
            List<PresentacionesDTO> editadas = new ArrayList<>();

            for(Presentaciones presentacion : presentaciones){

                presentacion.setDate(presentacionesEdit.getDate());
                presentacion.setLocation(presentacionesEdit.getLocation());
                presentacion.setAssistance(presentacionesEdit.getAssistance());
                Presentaciones guardada = this.presentacionesRepository.save(presentacion);
                editadas.add(this.convertirPresentacionesDTO(guardada));
            }
            return editadas;
        }
        return null;
    }
    public PresentacionesDTO findbyIdPresentation(Integer id){
        Optional<Presentaciones> cajilaId = this.presentacionesRepository.findById(id);
        if(cajilaId.isPresent()){
            return this.convertirPresentacionesDTO(cajilaId.get());
        }
        return null;
    }


}//fin de la clase
