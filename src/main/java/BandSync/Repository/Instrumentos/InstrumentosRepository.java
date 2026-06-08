package BandSync.Repository.Instrumentos;

import BandSync.Model.Instrumentos.Instrumentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface InstrumentosRepository extends JpaRepository <Instrumentos, Integer>{
    List <Instrumentos> findByName (String name);
}
