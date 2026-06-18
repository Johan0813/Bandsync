package BandSync.Service.Integrantes;

import BandSync.Model.Instrumentos.Instrumentos;
import BandSync.Model.Integrantes.Integrantes;
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

    public List<IntegrantesRequestDTO> convertirListIntegrantesDTO (List<Integrantes> integrantesList){
        List<IntegrantesRequestDTO> listDTO= new ArrayList<>();
        for (Integrantes integrantes: integrantesList){
            listDTO.add(this.convertirIntegrantesDTO(integrantes));
        }
        return listDTO;
    }

    public IntegrantesRequestDTO convertirIntegrantesDTO(Integrantes integrantes ){
        IntegrantesRequestDTO dto = new IntegrantesRequestDTO();
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
    public IntegrantesRequestDTO saveIntegrante(Integrantes integrante){

        if(this.repositoryInt.findByEmail(integrante.getEmail()) != null){
            throw new RuntimeException("El correo ya se encuentra registrado");
        }

        if(integrante.getInstrument() == null){
            throw new RuntimeException("Debe seleccionar un instrumento");
        }

        Optional<Instrumentos> optionalInstrumento = this.repositoryInst.findById(integrante.getInstrument().getId());

        if(optionalInstrumento.isEmpty()){
            throw new RuntimeException("El instrumento no existe");
        }

        Instrumentos instrumento = optionalInstrumento.get();

        if(instrumento.getQuantity()<= 0){
            throw new RuntimeException("No hay instrumentos disponibles");
        }

        instrumento.setQuantity(instrumento.getQuantity()-1);

        this.repositoryInst.save(instrumento);

        return this.convertirIntegrantesDTO(this.repositoryInt.save(integrante));
    }

    public List<IntegrantesRequestDTO> findAll (){
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

    public IntegrantesRequestDTO editIntegrante(Integer id, Integrantes integranteEdit) {

        Optional<Integrantes> optional = this.repositoryInt.findById(id);

        if (optional.isEmpty()) {
            throw new RuntimeException("El integrante no existe");

        }

            Integrantes integrante = optional.get();

            if (!integrante.getEmail().equals(integranteEdit.getEmail())
                    && this.repositoryInt.findByEmail(integranteEdit.getEmail()) != null) {
                throw new RuntimeException("El correo ya se encuentra registrado");
            }

            if (integranteEdit.getInstrument() != null) {

                Instrumentos instrumentoActual = integrante.getInstrument();
                Instrumentos instrumentoNuevo = integranteEdit.getInstrument();

                if (!instrumentoActual.getId().equals(instrumentoNuevo.getId())) {

                    Optional<Instrumentos> optionalInstrumento =
                            this.repositoryInst.findById(instrumentoNuevo.getId());

                    if (optionalInstrumento.isEmpty()) {
                        throw new RuntimeException("El instrumento no existe");
                    }

                    Instrumentos nuevo = optionalInstrumento.get();

                    if (nuevo.getQuantity() <= 0) {
                        throw new RuntimeException("No hay instrumentos disponibles");
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

            return this.convertirIntegrantesDTO(this.repositoryInt.save(integrante));
        }



    public IntegrantesRequestDTO findById(Integer id){

        Optional<Integrantes> optional = this.repositoryInt.findById(id);

        if(optional.isEmpty()){
            throw new RuntimeException("El integrante no existe");
        }

        return this.convertirIntegrantesDTO(optional.get());
    }

    public IntegrantesRequestDTO findByEmail (String email) {
        Integrantes integrantes = this.repositoryInt.findByEmail(email);

        if (integrantes == null){
            throw new RuntimeException("El correo no existe");
        }
        return this.convertirIntegrantesDTO(integrantes);
    }

    public List<IntegrantesRequestDTO> findByType(String type){
        List<Integrantes> integrantes = this.repositoryInt.findByType(type);

        if (integrantes==null){
            throw new RuntimeException("No existen integrantes con este rol");
        }
        return this.convertirListIntegrantesDTO(integrantes);
    }

    public List<IntegrantesRequestDTO> findBySection (String section){
        List<Integrantes> integrantes = this.repositoryInt.findBySection(section);

        if (integrantes.isEmpty()){
            throw new RuntimeException("No existen integrantes en esta seccion");
        }

        return this.convertirListIntegrantesDTO(integrantes);
    }

    public List<IntegrantesRequestDTO> findByName (String name){
        List<Integrantes> integrantes = this.repositoryInt.findByName(name);

        if (integrantes.isEmpty()){
            throw new RuntimeException("No existen integrantes con ese nombre");
        }
        return this.convertirListIntegrantesDTO(integrantes);
    }
    public List<IntegrantesRequestDTO> findAllByOrderByNameAsc(){
        List<Integrantes> integrantes = this.repositoryInt.findAllByOrderByNameAsc();

        if (integrantes.isEmpty()){
            throw new RuntimeException("No existen integrantes registrados");
        }
        return this.convertirListIntegrantesDTO(integrantes);
    }
    public IntegrantesRequestDTO findByEmailAndPassword(String email, String password){
        Integrantes integrantes = this.repositoryInt.findByEmailAndPassword(email, password);

        if (integrantes == null) {
            throw new RuntimeException("Credenciales incorrectas");
        }
        return this.convertirIntegrantesDTO(integrantes);
    }

    public Integrantes login (String email, String password){
        Integrantes integrantes = this.repositoryInt.verificarCredenciales(email, password);

        if (integrantes == null) {
            throw new RuntimeException("Credenciales incorrectas");
        }
        return this.repositoryInt.verificarCredenciales(email, password);
    }
}
