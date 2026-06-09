package BandSync.Service.Ensayos;

import BandSync.Model.Ensayos.Ensayos;
import BandSync.Model.Ensayos.EnsayosDTO;
import BandSync.Repository.Ensayos.EnsayosRepository;
import BandSync.Repository.Instrumentos.InstrumentosRepository;
import BandSync.Repository.Integrantes.IntegrantesRepository;
import BandSync.Repository.Presentaciones.PresentacionesRepository;
import BandSync.Service.Presentaciones.PresentacionesService;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

import java.util.List;

@Service
public class EnsayosService {
//inyección de dependencias
    @Autowired
    EnsayosRepository ensayosRepository;
    @Autowired
    InstrumentosRepository instrumentosRepository;
    @Autowired
    IntegrantesRepository integrantesRepository;
    @Autowired
    PresentacionesRepository presentacionesRepository;

    public List<EnsayosDTO> convertirListEnsayosDTO (List<Ensayos> ensayosList){
        List<EnsayosDTO> listDTO= new ArrayList<>();
        for (Ensayos ensayos: ensayosList){
            listDTO.add(this.convertirEnsayosDTO(ensayos));
        }
        return listDTO;
    }

    public EnsayosDTO convertirEnsayosDTO(Ensayos ensayos ){
        EnsayosDTO dto = new EnsayosDTO();
        dto.setDate(ensayos.getDate());
        dto.setAsistencia(ensayos.getAsistencia());
        dto.setIntegrante(ensayos.getIntegrante());
        dto.setSection(ensayos.getSection());
        return dto;
    }

}
