package BandSync.Service.Integrantes;

import BandSync.Model.Instrumentos.Instrumentos;
import BandSync.Model.Integrantes.Integrantes;
import BandSync.Model.Integrantes.IntegrantesRequestDTO;
import BandSync.Model.Integrantes.IntegrantesResponseDTO;
import BandSync.Repository.Instrumentos.InstrumentosRepository;
import BandSync.Repository.Integrantes.IntegrantesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<IntegrantesResponseDTO> convertirListIntegrantesDTO (List<Integrantes> integrantesList){
        List<IntegrantesResponseDTO> listDTO= new ArrayList<>();
        for (Integrantes integrantes: integrantesList){
            listDTO.add(this.convertirIntegrantesDTO(integrantes));
        }
        return listDTO;
    }

    public IntegrantesResponseDTO convertirIntegrantesDTO(Integrantes integrantes){
        if(integrantes.getInstrument() == null){
            return new IntegrantesResponseDTO(
                    integrantes.getId(),
                    integrantes.getName(),
                    integrantes.getEmail(),
                    integrantes.getAge(),
                    integrantes.getType(),
                    "Administrador",
                    0,
                    integrantes.getSection()
            );
        }
        return new IntegrantesResponseDTO(
                integrantes.getId(),
                integrantes.getName(),
                integrantes.getEmail(),
                integrantes.getAge(),
                integrantes.getType(),
                integrantes.getInstrument().getName(),
                integrantes.getInstrument().getId(),
                integrantes.getSection()
        );
    }
    public IntegrantesResponseDTO saveIntegrante(Integrantes integrante){
        if(this.repositoryInt.findByEmail(integrante.getEmail()) != null){
            throw new RuntimeException("El correo ya se encuentra registrado");
        }
        if(!integrante.getType().equalsIgnoreCase("ADMIN")
                && integrante.getInstrument() == null){
            throw new RuntimeException("Debe seleccionar un instrumento");
        }

        if(integrante.getInstrument() != null){

            Optional<Instrumentos> optionalInstrumento = this.repositoryInst.findById(integrante.getInstrument().getId());

            if(optionalInstrumento.isEmpty()){
                throw new RuntimeException("El instrumento no existe");
            }
            Instrumentos instrumento = optionalInstrumento.get();

            if(instrumento.getQuantity() <= 0){
                throw new RuntimeException("No hay instrumentos disponibles");
            }

            instrumento.setQuantity(instrumento.getQuantity() - 1);

            this.repositoryInst.save(instrumento);
        }

        integrante.setPassword(passwordEncoder.encode(integrante.getPassword()));

        return this.convertirIntegrantesDTO(this.repositoryInt.save(integrante));
    }

    public List<IntegrantesResponseDTO> findAll (){
        return this.convertirListIntegrantesDTO(this.repositoryInt.findAll());
    }

    public void deleteIntegrante(Integer id){

        Optional<Integrantes> optional = this.repositoryInt.findById(id);

        if(optional.isEmpty()) {
            throw new RuntimeException("El integrante no existe");
        }
            Integrantes integrante = optional.get();

            if(integrante.getInstrument() != null){

                Instrumentos instrumento = integrante.getInstrument();
                instrumento.setQuantity(instrumento.getQuantity() + 1);

                this.repositoryInst.save(instrumento);
            }

            this.repositoryInt.deleteById(id);

    }

    public IntegrantesResponseDTO editIntegrante(Integer id, Integrantes integranteEdit) {

        Optional<Integrantes> optional = this.repositoryInt.findById(id);

        if (optional.isEmpty()) {
            throw new RuntimeException("El integrante no existe");
        }

        Integrantes integrante = optional.get();

        if (!integrante.getEmail().equals(integranteEdit.getEmail())&& this.repositoryInt.findByEmail(integranteEdit.getEmail()) != null) {
            throw new RuntimeException("El correo ya se encuentra registrado");
        }

        if (!integrante.getType().equalsIgnoreCase("ADMIN") && integranteEdit.getInstrument() == null) {
            throw new RuntimeException("Debe seleccionar un instrumento");
        }

        if (integranteEdit.getInstrument() != null) {
            Instrumentos instrumentoActual = integrante.getInstrument();
            Instrumentos instrumentoNuevo = integranteEdit.getInstrument();

            Optional<Instrumentos> optionalInstrumento = this.repositoryInst.findById(instrumentoNuevo.getId());

            if (optionalInstrumento.isEmpty()) {
                throw new RuntimeException("El instrumento no existe");
            }

            Instrumentos nuevo = optionalInstrumento.get();

            if (instrumentoActual == null || !instrumentoActual.getId().equals(nuevo.getId())) {
                if (nuevo.getQuantity() <= 0) {
                    throw new RuntimeException("No hay instrumentos disponibles");
                }

                if (instrumentoActual != null) {

                    instrumentoActual.setQuantity(instrumentoActual.getQuantity() + 1);
                    this.repositoryInst.save(instrumentoActual);
                }

                nuevo.setQuantity(nuevo.getQuantity() - 1);
                this.repositoryInst.save(nuevo);
                integrante.setInstrument(nuevo);
            }
        }

        integrante.setName(integranteEdit.getName());
        integrante.setEmail(integranteEdit.getEmail());
        integrante.setAge(integranteEdit.getAge());
        integrante.setSection(integranteEdit.getSection());
        return this.convertirIntegrantesDTO(
                this.repositoryInt.save(integrante)
        );
    }


    public IntegrantesResponseDTO findById(Integer id){

        Optional<Integrantes> optional = this.repositoryInt.findById(id);

        if(optional.isEmpty()){
            throw new RuntimeException("El integrante no existe");
        }

        return this.convertirIntegrantesDTO(optional.get());
    }

    public IntegrantesResponseDTO findByEmail (String email) {
        Integrantes integrantes = this.repositoryInt.findByEmail(email);

        if (integrantes == null){
            throw new RuntimeException("El correo no existe");
        }
        return this.convertirIntegrantesDTO(integrantes);
    }

    public List<IntegrantesResponseDTO> findByType(String type){
        List<Integrantes> integrantes = this.repositoryInt.findByType(type);

        if (integrantes.isEmpty()){
            throw new RuntimeException("No existen integrantes con este rol");
        }
        return this.convertirListIntegrantesDTO(integrantes);
    }

    public List<IntegrantesResponseDTO> findBySection (String section){
        List<Integrantes> integrantes = this.repositoryInt.findBySection(section);

        if (integrantes.isEmpty()){
            throw new RuntimeException("No existen integrantes en esta seccion");
        }

        return this.convertirListIntegrantesDTO(integrantes);
    }

    public List<IntegrantesResponseDTO> findByName (String name){
        List<Integrantes> integrantes = this.repositoryInt.findByName(name);

        if (integrantes.isEmpty()){
            throw new RuntimeException("No existen integrantes con ese nombre");
        }
        return this.convertirListIntegrantesDTO(integrantes);
    }
    public List<IntegrantesResponseDTO> findAllByOrderByNameAsc(){
        List<Integrantes> integrantes = this.repositoryInt.findAllByOrderByNameAsc();

        if (integrantes.isEmpty()){
            throw new RuntimeException("No existen integrantes registrados");
        }
        return this.convertirListIntegrantesDTO(integrantes);
    }
}
