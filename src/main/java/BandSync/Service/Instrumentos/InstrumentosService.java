package BandSync.Service.Instrumentos;

import BandSync.Model.Instrumentos.Instrumentos;
import BandSync.Model.Instrumentos.InstrumentosRequestDTO;
import BandSync.Model.Instrumentos.InstrumentosResponseDTO;
import BandSync.Repository.Instrumentos.InstrumentosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InstrumentosService {

    @Autowired
    private InstrumentosRepository instrumentosRepository;

    public InstrumentosResponseDTO saveInstrumento(InstrumentosRequestDTO dto){

        if(dto.getQuantity() < 0){
            throw new RuntimeException("La cantidad no puede ser negativa");
        }

        List<Instrumentos> instrumentos =
                this.instrumentosRepository.findAll();

        for(Instrumentos instrumento : instrumentos){

            if(
                    instrumento.getName()
                            .trim()
                            .equalsIgnoreCase(
                                    dto.getName().trim()
                            )
            ){

                instrumento.setQuantity(
                        instrumento.getQuantity()
                                + dto.getQuantity()
                );

                return this.convertInstrumentoDTO(
                        this.instrumentosRepository.save(instrumento)
                );
            }
        }

        Instrumentos nuevo =
                new Instrumentos(dto.getName(), dto.getQuantity()
                );

        return this.convertInstrumentoDTO(this.instrumentosRepository.save(nuevo));
    }

    public List<InstrumentosResponseDTO> findAll(){
        return this.convertListDTO(
                this.instrumentosRepository.findAll()
        );
    }

    public InstrumentosResponseDTO findById(Integer id){

        Optional<Instrumentos> optional =
                this.instrumentosRepository.findById(id);

        if(optional.isEmpty()){
            throw new RuntimeException("El instrumento no existe");
        }

        return this.convertInstrumentoDTO(optional.get());
    }

    public InstrumentosResponseDTO deleteInstrumento(Integer id, Integer quantity){

        Optional<Instrumentos> optional =
                this.instrumentosRepository.findById(id);

        if(optional.isEmpty()){
            throw new RuntimeException("El instrumento no existe");
        }

        Instrumentos instrumento = optional.get();

        if(quantity > instrumento.getQuantity()){
            throw new RuntimeException("No hay suficiente cantidad disponible");
        }

        instrumento.setQuantity(
                instrumento.getQuantity() - quantity
        );

        return this.convertInstrumentoDTO(
                this.instrumentosRepository.save(instrumento)
        );
    }

    public InstrumentosResponseDTO editInstrumento(Integer id, InstrumentosRequestDTO dto){

        Optional<Instrumentos> optionalInst =
                this.instrumentosRepository.findById(id);

        if(optionalInst.isEmpty()){
            throw new RuntimeException("El instrumento no existe");
        }

        if(dto.getQuantity() < 0){
            throw new RuntimeException("La cantidad no puede ser negativa");
        }

        Instrumentos instrumento = optionalInst.get();

        instrumento.setQuantity(dto.getQuantity());

        return this.convertInstrumentoDTO(
                this.instrumentosRepository.save(instrumento)
        );
    }

    public List<InstrumentosResponseDTO> findByName(String name){

        List<Instrumentos> instrumentos =
                this.instrumentosRepository.findByName(name);

        if(instrumentos.isEmpty()){
            throw new RuntimeException("No existe instrumento con ese nombre");
        }

        return this.convertListDTO(instrumentos);
    }

    public InstrumentosResponseDTO convertInstrumentoDTO(
            Instrumentos instrumento){

        return new InstrumentosResponseDTO(
                instrumento.getId(),
                instrumento.getName(),
                instrumento.getQuantity()
        );
    }

    public List<InstrumentosResponseDTO> convertListDTO(
            List<Instrumentos> instrumentos){

        List<InstrumentosResponseDTO> dtoList =
                new ArrayList<>();

        for(Instrumentos instrumento : instrumentos){

            dtoList.add(
                    this.convertInstrumentoDTO(instrumento)
            );
        }

        return dtoList;
    }
}