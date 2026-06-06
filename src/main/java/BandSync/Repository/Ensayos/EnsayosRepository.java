package BandSync.Repository.Ensayos;

import BandSync.Model.Ensayos.Ensayos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EnsayosRepository extends JpaRepository <Ensayos, Integer> {
    List<Ensayos> findByDate(LocalDate date);
    List<Ensayos> findBySection(String section);

}
