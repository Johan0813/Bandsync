package BandSync.Service.Instrumentos;

import BandSync.Model.Instrumentos.Instrumentos;
import BandSync.Model.Instrumentos.InstrumentosDTO;
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

    public InstrumentosDTO saveInstrumento(Instrumentos instrumento){

        if (instrumento.getQuantity()<=0){
            throw new RuntimeException("La cantidad debe ser mayor a 0");
        }
        List<Instrumentos> instrumentos = this.instrumentosRepository.findByName(instrumento.getName());

        if(!instrumentos.isEmpty()){

            Instrumentos existente = instrumentos.get(0);

            existente.setQuantity(existente.getQuantity() + instrumento.getQuantity());

            return this.convertInstrumentoDTO(this.instrumentosRepository.save(existente));
        }

        return this.convertInstrumentoDTO(this.instrumentosRepository.save(instrumento));
    }

    public List<InstrumentosDTO> findAll(){
        return this.convertListDTO(this.instrumentosRepository.findAll()
        );
    }

    public InstrumentosDTO findById (Integer id){
        Optional<Instrumentos> optional = this.instrumentosRepository.findById(id);

        if (optional.isEmpty()){
            throw new RuntimeException("El instrumento no existe");
        }
            return this.convertInstrumentoDTO(optional.get());
    }

    public InstrumentosDTO deleteInstrumento(Integer id, Integer quantity){

        Optional<Instrumentos> optional =
                this.instrumentosRepository.findById(id);

        if(optional.isEmpty()) {
            throw new RuntimeException("El instrumento no existe");
        }
            Instrumentos instrumento = optional.get();

            if(quantity > instrumento.getQuantity()) {
                throw new RuntimeException("No hay suficiente cantidad disponible");
            }
                instrumento.setQuantity(
                        instrumento.getQuantity() - quantity
                );

                return this.convertInstrumentoDTO(
                        this.instrumentosRepository.save(instrumento)
                );

    }
    public InstrumentosDTO editInstrumento (Integer id, Instrumentos instrumentoEdit){
        Optional<Instrumentos> optionalInst = this.instrumentosRepository.findById(id);

        if (optionalInst.isEmpty()) {
            throw new RuntimeException("El instrumento no existe");
        }
            if (instrumentoEdit.getQuantity()<0){
                throw new RuntimeException("La cantidad no puede ser negativa");
            }
            Instrumentos instrumento = optionalInst.get();
            instrumento.setQuantity(instrumentoEdit.getQuantity());

            return this.convertInstrumentoDTO(this.instrumentosRepository.save(instrumento));
        }

    public List<InstrumentosDTO> findByName (String name){
        List<Instrumentos> instrumentos = this.instrumentosRepository.findByName(name);

        if (instrumentos.isEmpty()){
            throw new RuntimeException("No existe instrumento con ese nombre");
        }
        return this.convertListDTO(instrumentos);
    }


    public InstrumentosDTO convertInstrumentoDTO(
            Instrumentos instrumento){

        InstrumentosDTO dto = new InstrumentosDTO();

        dto.setName(instrumento.getName());
        dto.setQuantity(instrumento.getQuantity());

        return dto;
    }
    public List<InstrumentosDTO> convertListDTO(
            List<Instrumentos> instrumentos){

        List<InstrumentosDTO> dtoList =
                new ArrayList<>();

        for(Instrumentos instrumento : instrumentos){

            dtoList.add(
                    convertInstrumentoDTO(instrumento)
            );
        }

        return dtoList;
    }
}
