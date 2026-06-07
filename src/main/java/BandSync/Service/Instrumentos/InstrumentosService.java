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

        Optional<Instrumentos> opt =
                this.instrumentosRepository.findById(instrumento.getId());

        if(opt.isPresent()){
            return null;
        }

        return this.convertInstrumentoDTO(
                this.instrumentosRepository.save(instrumento)
        );
    }
    public List<InstrumentosDTO> findAll(){
        return this.convertListDTO(
                this.instrumentosRepository.findAll()
        );
    }

    public InstrumentosDTO findByIdIntrumento (Integer id){
        Optional<Instrumentos> optional = this.instrumentosRepository.findById(id);

        if (optional.isPresent()){
            return this.convertInstrumentoDTO(optional.get());
        }
        return null;
    }

    public void deleteInstrumento (Integer id){
        this.instrumentosRepository.deleteById(id);
    }

    public InstrumentosDTO editInstrumento (Integer id, Instrumentos instrumentoEdit){
        Optional<Instrumentos> optionalInst = this.instrumentosRepository.findById(id);

        if (optionalInst.isPresent()){
            Instrumentos instrumento = optionalInst.get();
            instrumento.setName(instrumentoEdit.getName());
            instrumento.setCondition(instrumentoEdit.getCondition());
            instrumento.setAvailability(instrumentoEdit.getAvailability());
            instrumento.setQuantity(instrumentoEdit.getQuantity());

            return this.convertInstrumentoDTO(this.instrumentosRepository.save(instrumento));
        }
        return null;
    }

    public List<InstrumentosDTO> findByName (String name){
        return this.convertListDTO(this.instrumentosRepository.findByName(name));
    }

    public List<InstrumentosDTO> findByAvailability (String availability){
        return this.convertListDTO(this.instrumentosRepository.findByAvailability(availability));
    }

    public List<InstrumentosDTO> findByCondition (String condition){
        return this.convertListDTO(this.instrumentosRepository.findByCondition(condition));
    }
    public InstrumentosDTO convertInstrumentoDTO(
            Instrumentos instrumento){

        InstrumentosDTO dto = new InstrumentosDTO();

        dto.setName(instrumento.getName());
        dto.setCondition(instrumento.getCondition());
        dto.setAvailability(instrumento.getAvailability());
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
