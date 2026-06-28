package BandSync.Repository.Presentaciones;

import BandSync.Model.Presentaciones.Presentaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface PresentacionesRepository extends JpaRepository <Presentaciones, Integer> {
    List<Presentaciones> findByDate (LocalDateTime date);
    List<Presentaciones> findByLocation(String location);
    List<Presentaciones> findByAssistance (String assistance);
}//fin
