package BandSync.Service.Presentaciones;

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
    EnsayosRepository ensayosRepository;
    @Autowired
    IntegrantesRepository integrantesRepository;
    @Autowired
    InstrumentosRepository instrumentosRepository;

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
        this.presentacionesRepository.deleteById(id);
    }

    public PresentacionesDTO savePresentation (Presentaciones presentaciones){
        Optional <Presentaciones> cajta= this.presentacionesRepository.findById(presentaciones.getId());
        if (cajta.isPresent()){
            return null;
        }
        return this.convertirPresentacionesDTO(this.presentacionesRepository.save(presentaciones));
    }

    public PresentacionesDTO editPresentation (Integer id, Presentaciones presentacionesEdit){
        Optional <Presentaciones> cajtaPresentacionNueva = this.presentacionesRepository.findById(id);
        if (cajtaPresentacionNueva.isPresent()){
            Presentaciones presentaciones = cajtaPresentacionNueva.get();
            presentaciones.setLocation(presentacionesEdit.getLocation());
            presentaciones.setDate(presentacionesEdit.getDate());
            presentaciones.setAssistance(presentacionesEdit.getAssistance());
            presentaciones.setIntegrante(presentacionesEdit.getIntegrante());

            return this.convertirPresentacionesDTO(this.presentacionesRepository.save(presentaciones));
        }
        return null;
    }
    public Presentaciones findbyIdPresentaion(Integer id){
        Optional<Presentaciones> cajilaId = this.presentacionesRepository.findById(id);
        if(cajilaId.isPresent()){
            return cajilaId.get();
        }
        return null;
    }


}//fin de la clase
