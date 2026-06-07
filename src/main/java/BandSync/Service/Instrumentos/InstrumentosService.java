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
            return null;
        }
        List<Instrumentos> instrumentos =
                this.instrumentosRepository.findByName(instrumento.getName());

        if(!instrumentos.isEmpty()){

            Instrumentos existente = instrumentos.get(0);

            existente.setQuantity(existente.getQuantity() + instrumento.getQuantity());

            return this.convertInstrumentoDTO(this.instrumentosRepository.save(existente));
        }

        return this.convertInstrumentoDTO(this.instrumentosRepository.save(instrumento));
    }

    public List<InstrumentosDTO> findAll(){
        return this.convertListDTO(
                this.instrumentosRepository.findAll()
        );
    }

    public InstrumentosDTO findByIdInstrumento (Integer id){
        Optional<Instrumentos> optional = this.instrumentosRepository.findById(id);

        if (optional.isPresent()){
            return this.convertInstrumentoDTO(optional.get());
        }
        return null;
    }

    public InstrumentosDTO deleteInstrumento(Integer id, Integer quantity){

        Optional<Instrumentos> optional =
                this.instrumentosRepository.findById(id);

        if(optional.isPresent()){

            Instrumentos instrumento = optional.get();

            if(quantity <= instrumento.getQuantity()){

                instrumento.setQuantity(
                        instrumento.getQuantity() - quantity
                );

                return this.convertInstrumentoDTO(
                        this.instrumentosRepository.save(instrumento)
                );
            }
        }

        return null;
    }
    public InstrumentosDTO editInstrumento (Integer id, Instrumentos instrumentoEdit){
        Optional<Instrumentos> optionalInst = this.instrumentosRepository.findById(id);

        if (optionalInst.isPresent()){
            if (instrumentoEdit.getQuantity()<0){
                return null;
            }
            Instrumentos instrumento = optionalInst.get();
            instrumento.setQuantity(instrumentoEdit.getQuantity());

            return this.convertInstrumentoDTO(this.instrumentosRepository.save(instrumento));
        }
        return null;
    }

    public List<InstrumentosDTO> findByName (String name){
        return this.convertListDTO(this.instrumentosRepository.findByName(name));
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
