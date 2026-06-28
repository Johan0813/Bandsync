package BandSync.Repository.Ensayos;

import BandSync.Model.Ensayos.Ensayos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface EnsayosRepository extends JpaRepository <Ensayos, Integer> {
    List<Ensayos> findByDate(LocalDateTime date);
    List<Ensayos> findBySection(String section);
    List<Ensayos> findByAssistance(String assistance);

}
