package BandSync.Service.Presentaciones;

import BandSync.Model.Integrantes.Integrantes;
import BandSync.Model.Presentaciones.Presentaciones;
import BandSync.Model.Presentaciones.PresentacionesRequestDTO;
import BandSync.Model.Presentaciones.PresentacionesResponseDTO;
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
    public  List<PresentacionesResponseDTO> convertirListPresentacionesDTO (List<Presentaciones> presentacionesList){
        List<PresentacionesResponseDTO> listDTO= new ArrayList<>();
        for (Presentaciones presentaciones: presentacionesList){
            listDTO.add(this.convertirPresentacionesDTO(presentaciones));
        }
        return listDTO;
    }

    public PresentacionesResponseDTO convertirPresentacionesDTO(Presentaciones presentaciones){
        return new PresentacionesResponseDTO(presentaciones.getId(), presentaciones.getDate(), presentaciones.getLocation(), presentaciones.getIntegrante().getName(), presentaciones.getIntegrante().getId(), presentaciones.getAssistance());
    }


    public List<PresentacionesResponseDTO> findByDate(LocalDate date){
        List<Presentaciones> presentaciones = this.presentacionesRepository.findByDate(date);

        if (presentaciones.isEmpty()){
            throw new RuntimeException("No existe presentaciones en la fecha indicada");
        }
        return this.convertirListPresentacionesDTO(presentaciones);
    }

    public List<PresentacionesResponseDTO> findByLocation(String location){
        List<Presentaciones> presentaciones = this.presentacionesRepository.findByLocation(location);

        if (presentaciones.isEmpty()){
            throw new RuntimeException("No existen presentaciones en el lugar indicado");
        }
        return this.convertirListPresentacionesDTO(presentaciones);
    }

    public List<PresentacionesResponseDTO> findByAssistance (String assistance){
        List<Presentaciones> presentaciones = this.presentacionesRepository.findByAssistance(assistance);
        if (presentaciones.isEmpty()){
            throw new RuntimeException("No hay lista de asistencia en este momento");
        }
        return this.convertirListPresentacionesDTO(presentaciones);
    }

    public List<PresentacionesResponseDTO> findAll(){
        return this.convertirListPresentacionesDTO(this.presentacionesRepository.findAll());
    }

    public void deletePresentation (Integer id){
        Optional<Presentaciones> optional = this.presentacionesRepository.findById(id);

        if (optional.isEmpty()) {
            throw new RuntimeException("La presentacion no existe");
        }
            LocalDate date = optional.get().getDate();

            List<Presentaciones> presentaciones = this.presentacionesRepository.findByDate(date);

            for (Presentaciones presentacion : presentaciones){
                this.presentacionesRepository.delete(presentacion);
            }
        }


    public List<PresentacionesResponseDTO> savePresentation(PresentacionesRequestDTO dto){

        if(!this.presentacionesRepository.findByDate(dto.getDate()).isEmpty()){
            throw new RuntimeException("Ya existe una presentacion para esa fecha");
        }
        List<Integrantes> integrantes =
                this.integrantesRepository.findAll();

        if(integrantes.isEmpty()){
            throw new RuntimeException("No existen integrantes registrados");
        }

        List<PresentacionesResponseDTO> presentacionesCreadas = new ArrayList<>();

        for(Integrantes integrante : integrantes){

            Presentaciones guardada =
                    this.presentacionesRepository.save(new Presentaciones(dto.getDate(), dto.getLocation(), integrante, dto.getAsistencia()));

            presentacionesCreadas.add(this.convertirPresentacionesDTO(guardada));
        }

        return presentacionesCreadas;
    }


    public List<PresentacionesResponseDTO> editPresentation(Integer id, PresentacionesRequestDTO presentacionesEdit){

        Optional<Presentaciones> optional = this.presentacionesRepository.findById(id);

        if(optional.isEmpty()){
            throw new RuntimeException("La presentacion no existe");
        }

        LocalDate fechaOriginal = optional.get().getDate();

        if(!fechaOriginal.equals(presentacionesEdit.getDate()) && ! this.presentacionesRepository.findByDate(presentacionesEdit.getDate()).isEmpty()){
            throw new RuntimeException("Ya existe una presentacion para esa fecha");
        }

        List<Presentaciones> presentaciones = this.presentacionesRepository.findByDate(fechaOriginal);

        List<PresentacionesResponseDTO> editadas = new ArrayList<>();

        for(Presentaciones presentacion : presentaciones){
            presentacion.setDate(presentacionesEdit.getDate());
            presentacion.setLocation(presentacionesEdit.getLocation());
            presentacion.setAssistance(presentacionesEdit.getAsistencia());

            Presentaciones guardada = this.presentacionesRepository.save(presentacion);

            editadas.add(this.convertirPresentacionesDTO(guardada));
        }

        return editadas;
    }
    public PresentacionesResponseDTO findById(Integer id){

        Optional<Presentaciones> presentacion = this.presentacionesRepository.findById(id);

        if(presentacion.isEmpty()) {
            throw new RuntimeException("La presentacion no existe");
        }
            return this.convertirPresentacionesDTO(presentacion.get());
        }



}//fin de la clase
