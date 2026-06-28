package BandSync.Service.Ensayos;

import BandSync.Model.Ensayos.Ensayos;
import BandSync.Model.Ensayos.EnsayosRequestDTO;
import BandSync.Model.Ensayos.EnsayosResponseDTO;
import BandSync.Model.Integrantes.Integrantes;
import BandSync.Repository.Ensayos.EnsayosRepository;
import BandSync.Repository.Integrantes.IntegrantesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnsayosService {

    @Autowired
    EnsayosRepository ensayosRepository;

    @Autowired
    IntegrantesRepository integrantesRepository;

    public List<EnsayosResponseDTO> convertirListEnsayosDTO(List<Ensayos> ensayosList){
        List<EnsayosResponseDTO> listDTO = new ArrayList<>();

        for(Ensayos ensayos : ensayosList){
            listDTO.add(this.convertirEnsayosDTO(ensayos));
        }

        return listDTO;
    }

    public EnsayosResponseDTO convertirEnsayosDTO(Ensayos ensayos){
        return new EnsayosResponseDTO(ensayos.getId(), ensayos.getDate(), ensayos.getSection(), ensayos.getIntegrante().getName(), ensayos.getIntegrante().getId(), ensayos.getAssistance());
    }

    public List<EnsayosResponseDTO> findByDate(LocalDate date){

        List<Ensayos> ensayos = this.ensayosRepository.findByDate(date);

        if(ensayos.isEmpty()){
            throw new RuntimeException("No existen ensayos en la fecha indicada");
        }

        return this.convertirListEnsayosDTO(ensayos);
    }

    public List<EnsayosResponseDTO> findBySection(String section){

        List<Ensayos> ensayos = this.ensayosRepository.findBySection(section);

        if(ensayos.isEmpty()){
            throw new RuntimeException("La sección que indicaste no esta disponible");
        }

        return this.convertirListEnsayosDTO(ensayos);
    }

    public List<EnsayosResponseDTO> findByAssistance(String assistance){

        List<Ensayos> ensayos = this.ensayosRepository.findByAssistance(assistance);

        if(ensayos.isEmpty()){
            throw new RuntimeException("No hay lista de asistencia en este momento");
        }

        return this.convertirListEnsayosDTO(ensayos);
    }

    public List<EnsayosResponseDTO> findAll(){

        return this.convertirListEnsayosDTO(
                this.ensayosRepository.findAll()
        );
    }

    public void deleteEnsayo(Integer id){

        Optional<Ensayos> optional =
                this.ensayosRepository.findById(id);

        if(optional.isEmpty()){
            throw new RuntimeException("El Ensayo no existe");
        }

        LocalDateTime date = optional.get().getDate();

        List<Ensayos> ensayos =
                this.ensayosRepository.findByDate(date);

        for(Ensayos ensayo : ensayos){
            this.ensayosRepository.delete(ensayo);
        }
    }

    public List<EnsayosResponseDTO> saveEnsayo(EnsayosRequestDTO dto){

        if(!this.ensayosRepository.findByDate(dto.getDate()).isEmpty()){
            throw new RuntimeException("Ya existe un ensayo para esta fecha!");
        }

        List<Integrantes> integrantes =
                this.integrantesRepository.findAll();

        List<EnsayosResponseDTO> ensayosCreados =
                new ArrayList<>();

        for(Integrantes integrante : integrantes){

            Ensayos nuevo = new Ensayos();

            nuevo.setDate(dto.getDate());
            nuevo.setAssistance(dto.getAssistance());
            nuevo.setSection(dto.getSection());
            nuevo.setIntegrante(integrante);

            Ensayos agendado =
                    this.ensayosRepository.save(nuevo);

            ensayosCreados.add(
                    this.convertirEnsayosDTO(agendado)
            );
        }

        return ensayosCreados;
    }

    public List<EnsayosResponseDTO> editEnsayo(Integer id, EnsayosRequestDTO dto){

        Optional<Ensayos> optional =
                this.ensayosRepository.findById(id);

        if(optional.isEmpty()){
            throw new RuntimeException("El ensayo no existe");
        }

        LocalDate fechaYaEstablecida =
                optional.get().getDate();

        if(!fechaYaEstablecida.equals(dto.getDate())
                && !this.ensayosRepository.findByDate(dto.getDate()).isEmpty()){

            throw new RuntimeException("Ya existe un ensayo en esta fecha!");
        }

        List<Ensayos> ensayos =
                this.ensayosRepository.findByDate(fechaYaEstablecida);

        List<EnsayosResponseDTO> editados =
                new ArrayList<>();

        for(Ensayos ensayo : ensayos){

            ensayo.setDate(dto.getDate());
            ensayo.setSection(dto.getSection());
            ensayo.setAssistance(dto.getAssistance());

            Ensayos agendado =
                    this.ensayosRepository.save(ensayo);

            editados.add(
                    this.convertirEnsayosDTO(agendado)
            );
        }

        return editados;
    }

    public EnsayosResponseDTO findById(Integer id){

        Optional<Ensayos> ensayo =
                this.ensayosRepository.findById(id);

        if(ensayo.isEmpty()){
            throw new RuntimeException("El ensayo no existe!");
        }

        return this.convertirEnsayosDTO(
                ensayo.get()
        );
    }

}