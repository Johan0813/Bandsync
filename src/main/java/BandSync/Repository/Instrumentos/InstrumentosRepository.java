package BandSync.Repository.Instrumentos;

import BandSync.Model.Instrumentos.Instrumentos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstrumentosRepository extends JpaRepository <Instrumentos, Integer>{
    List <Instrumentos> findByName (String name);
    List <Instrumentos> findByCondition (String condition);
    List <Instrumentos> findByAvailability (String availability);
}
