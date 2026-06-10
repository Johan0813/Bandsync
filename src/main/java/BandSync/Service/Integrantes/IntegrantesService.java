package BandSync.Service.Integrantes;

import BandSync.Model.Instrumentos.Instrumentos;
import BandSync.Model.Integrantes.Integrantes;
import BandSync.Model.Integrantes.IntegrantesDTO;
import BandSync.Repository.Instrumentos.InstrumentosRepository;
import BandSync.Repository.Integrantes.IntegrantesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service


public class IntegrantesService {

    @Autowired
    private IntegrantesRepository repositoryInt;
    @Autowired
    private InstrumentosRepository repositoryInst;

    public List<IntegrantesDTO> convertirListIntegrantesDTO (List<Integrantes> integrantesList){
        List<IntegrantesDTO> listDTO= new ArrayList<>();
        for (Integrantes integrantes: integrantesList){
            listDTO.add(this.convertirIntegrantesDTO(integrantes));
        }
        return listDTO;
    }

    public IntegrantesDTO convertirIntegrantesDTO(Integrantes integrantes ){
        IntegrantesDTO dto = new IntegrantesDTO();
        dto.setName(integrantes.getName());
        dto.setEmail(integrantes.getEmail());
        dto.setAge(integrantes.getAge());
        dto.setType(integrantes.getType());
        dto.setSection(integrantes.getSection());
        dto.setPassword(integrantes.getPassword());

        if(integrantes.getInstrument() != null){

            dto.setInstrument(integrantes.getInstrument().getName());
            dto.setInstrumentId(integrantes.getInstrument().getId());
        }

        return dto;
    }
    public IntegrantesDTO saveIntegrante(Integrantes integrante){

        if(this.repositoryInt.findByEmail(integrante.getEmail()) != null){
            return null;
        }

        if(integrante.getInstrument() == null){
            return null;
        }

        Optional<Instrumentos> optionalInstrumento =
                this.repositoryInst.findById(integrante.getInstrument().getId());

        if(optionalInstrumento.isEmpty()){
            return null;
        }

        Instrumentos instrumento = optionalInstrumento.get();

        if(instrumento.getQuantity()<= 0){
            return null;
        }

        instrumento.setQuantity(instrumento.getQuantity()-1);

        this.repositoryInst.save(instrumento);

        return this.convertirIntegrantesDTO(
                this.repositoryInt.save(integrante));
    }

    public List<IntegrantesDTO> findAll (){
        return this.convertirListIntegrantesDTO(this.repositoryInt.findAll());
    }
    public void deleteIntegrante(Integer id){

        Optional<Integrantes> optional = this.repositoryInt.findById(id);

        if(optional.isPresent()){

            Integrantes integrante = optional.get();

            if(integrante.getInstrument() != null){

                Instrumentos instrumento = integrante.getInstrument();
                instrumento.setQuantity(instrumento.getQuantity() + 1);

                this.repositoryInst.save(instrumento);
            }

            this.repositoryInt.deleteById(id);
        }
    }

    public IntegrantesDTO editIntegrante(Integer id, Integrantes integranteEdit){

        Optional<Integrantes> optional = this.repositoryInt.findById(id);

        if(optional.isPresent()){

            Integrantes integrante = optional.get();

            if(!integrante.getEmail().equals(integranteEdit.getEmail())
                    && this.repositoryInt.findByEmail(integranteEdit.getEmail()) != null){
                return null;
            }

            if(integranteEdit.getInstrument() != null){

                Instrumentos instrumentoActual = integrante.getInstrument();
                Instrumentos instrumentoNuevo = integranteEdit.getInstrument();

                if(!instrumentoActual.getId().equals(instrumentoNuevo.getId())){

                    Optional<Instrumentos> optionalInstrumento =
                            this.repositoryInst.findById(instrumentoNuevo.getId());

                    if(optionalInstrumento.isEmpty()){
                        return null;
                    }

                    Instrumentos nuevo = optionalInstrumento.get();

                    if(nuevo.getQuantity() <= 0){
                        return null;
                    }

                    instrumentoActual.setQuantity(instrumentoActual.getQuantity() + 1);
                    nuevo.setQuantity(nuevo.getQuantity() - 1);

                    this.repositoryInst.save(instrumentoActual);
                    this.repositoryInst.save(nuevo);

                    integrante.setInstrument(nuevo);
                }
            }

            integrante.setName(integranteEdit.getName());
            integrante.setEmail(integranteEdit.getEmail());
            integrante.setPassword(integranteEdit.getPassword());
            integrante.setAge(integranteEdit.getAge());
            integrante.setType(integranteEdit.getType());
            integrante.setSection(integranteEdit.getSection());

            return this.convertirIntegrantesDTO(
                    this.repositoryInt.save(integrante));
        }

        return null;
    }

    public IntegrantesDTO findById(Integer id){

        Optional<Integrantes> optional = this.repositoryInt.findById(id);

        if(optional.isPresent()){
            return this.convertirIntegrantesDTO(optional.get());
        }

        return null;
    }

    public IntegrantesDTO findByEmail (String email) {
        return this.convertirIntegrantesDTO(this.repositoryInt.findByEmail(email));
    }

    public List<IntegrantesDTO> findByType(String type){
        return this.convertirListIntegrantesDTO(this.repositoryInt.findByType(type));
    }

    public List<IntegrantesDTO> findBySection (String section){
        return this.convertirListIntegrantesDTO(this.repositoryInt.findBySection(section));
    }

    public List<IntegrantesDTO> findByName (String name){
        return this.convertirListIntegrantesDTO(this.repositoryInt.findByName(name));
    }
    public List<IntegrantesDTO> findAllByOrderByNameAsc(){
        return this.convertirListIntegrantesDTO(this.repositoryInt.findAllByOrderByNameAsc());
    }
    public IntegrantesDTO findByEmailAndPassword(String email, String password){
        return this.convertirIntegrantesDTO(this.repositoryInt.findByEmailAndPassword(email, password));
    }

    public Integrantes login (String email, String password){
        return this.repositoryInt.verificarCredenciales(email, password);
    }
}
