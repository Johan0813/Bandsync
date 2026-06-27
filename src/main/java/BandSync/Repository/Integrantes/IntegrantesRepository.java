package BandSync.Repository.Integrantes;

import BandSync.Model.Integrantes.Integrantes;
import BandSync.Model.Integrantes.IntegrantesResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IntegrantesRepository extends JpaRepository <Integrantes, Integer> {
    List<Integrantes> findByName(String name);

   Optional<Integrantes> findByEmail(String email);

    List<Integrantes> findByType(String type);

    List<Integrantes> findBySection(String section);

    List<Integrantes> findAllByOrderByNameAsc();

    @Query("SELECT i FROM Integrantes i WHERE i.type = :type")
    List<Integrantes> buscarType(@Param("type") String type);

    @Query("SELECT i FROM Integrantes i WHERE i.section = :section")
    List<Integrantes> buscarSection(@Param("section") String section);

    @Query("SELECT i FROM Integrantes i WHERE i.email = :email AND i.password = :password")
    Optional<Integrantes> verificarCredenciales(
            @Param("email") String email,
            @Param("password") String password
    );

}
