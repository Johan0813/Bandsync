package BandSync.Repository.Ensayos;

import BandSync.Model.Ensayos.Ensayos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface EnsayosRepository extends JpaRepository <Ensayos, Integer> {
    List<Ensayos> findByDate(LocalDate date);
    List<Ensayos> findBySection(String section);

}
