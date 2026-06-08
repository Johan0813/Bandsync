package BandSync.Repository.Presentaciones;

import BandSync.Model.Presentaciones.Presentaciones;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PresentacionesRepository extends JpaRepository <Presentaciones, Integer> {
    List<Presentaciones> findByDate (LocalDate date);
    List<Presentaciones> findByLocation(String location);
}//fin
