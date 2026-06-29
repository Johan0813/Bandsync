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
    public IntegrantesResponseDTO saveIntegrante(IntegrantesRequestDTO dto){

        if(this.repositoryInt.findByEmail(dto.getEmail()).isPresent()){
            throw new RuntimeException("El correo ya se encuentra registrado");
        }

        Instrumentos instrumento = null;

        if(!dto.getType().equalsIgnoreCase("ADMIN")){

            if(dto.getInstrumentId() == null){
                throw new RuntimeException("Debe seleccionar un instrumento");
            }
            if(dto.getPassword() == null || dto.getPassword().isEmpty()){
                throw new RuntimeException("La contraseña es obligatoria");
            }

            Optional<Instrumentos> optionalInstrumento =
                    this.repositoryInst.findById(dto.getInstrumentId());

            if(optionalInstrumento.isEmpty()){
                throw new RuntimeException("El instrumento no existe");
            }

            instrumento = optionalInstrumento.get();

            if(instrumento.getQuantity() <= 0){
                throw new RuntimeException("No hay instrumentos disponibles");
            }

            instrumento.setQuantity(instrumento.getQuantity() - 1);

            this.repositoryInst.save(instrumento);
        }

        return this.convertirIntegrantesDTO(
                this.repositoryInt.save(new Integrantes(dto.getName(), dto.getEmail(), passwordEncoder.encode(dto.getPassword()), dto.getAge(), dto.getType(), instrumento, dto.getSection())));
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

    public IntegrantesResponseDTO editIntegrante(Integer id, IntegrantesRequestDTO dto){

        Optional<Integrantes> optional = this.repositoryInt.findById(id);

        if(optional.isEmpty()){
            throw new RuntimeException("El integrante no existe");
        }

        Integrantes integrante = optional.get();

        if(!integrante.getEmail().equals(dto.getEmail()) && this.repositoryInt.findByEmail(dto.getEmail()).isPresent()){
            throw new RuntimeException("El correo ya se encuentra registrado");
        }

        if(!integrante.getType().equalsIgnoreCase("ADMIN") && dto.getInstrumentId() == null){
            throw new RuntimeException("Debe seleccionar un instrumento");
        }

        if(dto.getInstrumentId() != null){

            Instrumentos instrumentoActual = integrante.getInstrument();

            Optional<Instrumentos> optionalInstrumento =
                    this.repositoryInst.findById(dto.getInstrumentId());

            if(optionalInstrumento.isEmpty()){
                throw new RuntimeException("El instrumento no existe");
            }

            Instrumentos nuevo = optionalInstrumento.get();

            if(instrumentoActual == null || !instrumentoActual.getId().equals(nuevo.getId())){

                if(nuevo.getQuantity() <= 0){
                    throw new RuntimeException("No hay instrumentos disponibles");
                }

                if(instrumentoActual != null){

                    instrumentoActual.setQuantity(instrumentoActual.getQuantity() + 1);

                    this.repositoryInst.save(instrumentoActual);
                }

                nuevo.setQuantity(nuevo.getQuantity() - 1);

                this.repositoryInst.save(nuevo);

                integrante.setInstrument(nuevo);
            }
        }

        integrante.setName(dto.getName());
        integrante.setEmail(dto.getEmail());
        integrante.setAge(dto.getAge());
        integrante.setSection(dto.getSection());

        this.repositoryInt.save(integrante);

        return this.convertirIntegrantesDTO(integrante);
    }

    public IntegrantesResponseDTO findById(Integer id){

        Optional<Integrantes> optional = this.repositoryInt.findById(id);

        if(optional.isEmpty()){
            throw new RuntimeException("El integrante no existe");
        }

        return this.convertirIntegrantesDTO(optional.get());
    }

    public IntegrantesResponseDTO findByEmail (String email) {
        Optional <Integrantes> integrantes = this.repositoryInt.findByEmail(email);

        if (integrantes.isEmpty()){
            throw new RuntimeException("El correo no existe");
        }
        return this.convertirIntegrantesDTO(integrantes.get());
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
