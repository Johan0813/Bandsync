package BandSync.Repository.Presentaciones;

import BandSync.Model.Ensayos.Ensayos;
import BandSync.Model.Presentaciones.Presentaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface PresentacionesRepository extends JpaRepository <Presentaciones, Integer> {
    List<Presentaciones> findByDate (LocalDate date);
    List<Presentaciones> findByLocation(String location);
    List<Presentaciones> findByAssistance (String assistance);
}//fin
