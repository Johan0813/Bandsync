package BandSync;

import BandSync.Model.Ensayos.Ensayos;
import BandSync.Model.Ensayos.EnsayosRequestDTO;
import BandSync.Model.Ensayos.EnsayosResponseDTO;
import BandSync.Model.Integrantes.Integrantes;
import BandSync.Repository.Ensayos.EnsayosRepository;
import BandSync.Repository.Integrantes.IntegrantesRepository;
import BandSync.Service.Ensayos.EnsayosService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EnsayosServiceTest {

    @Mock
    private EnsayosRepository ensayosRepository;

    @Mock
    private IntegrantesRepository integrantesRepository;

    @InjectMocks
    private EnsayosService ensayosService;

    @Test
    void convertirEnsayosDTO() {

        Integrantes integrante = new Integrantes();
        integrante.setId(1);
        integrante.setName("Juan");

        Ensayos ensayo = new Ensayos();
        ensayo.setId(1);
        ensayo.setDate(LocalDateTime.of(2026, 1, 1, 9, 7));
        ensayo.setAssistance("Presente");
        ensayo.setSection("Bronces");
        ensayo.setIntegrante(integrante);

        EnsayosResponseDTO dto =
                ensayosService.convertirEnsayosDTO(ensayo);

        assertNotNull(dto);
        assertEquals(1, dto.getId());
        assertEquals("Juan", dto.getIntegrante());
    }

    @Test
    void findByIdExitoso() {

        Integrantes integrante = new Integrantes();
        integrante.setId(1);
        integrante.setName("Juan");

        Ensayos ensayo = new Ensayos();
        ensayo.setId(1);
        ensayo.setIntegrante(integrante);

        when(ensayosRepository.findById(1))
                .thenReturn(Optional.of(ensayo));

        EnsayosResponseDTO response =
                ensayosService.findById(1);

        assertNotNull(response);
        assertEquals(1, response.getId());
    }

    @Test
    void findByIdNoExiste() {

        when(ensayosRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> ensayosService.findById(1)
        );
    }
    @Test
    void findByDateExitoso(){

        Integrantes integrante = new Integrantes();
        integrante.setId(1);
        integrante.setName("Juan");

        Ensayos ensayo = new Ensayos();
        ensayo.setIntegrante(integrante);

        when(ensayosRepository.findByDate(any()))
                .thenReturn(List.of(ensayo));

        List<EnsayosResponseDTO> lista =
                ensayosService.findByDate(LocalDateTime.now());

        assertEquals(1, lista.size());
    }

    @Test
    void findByDateVacio(){

        when(ensayosRepository.findByDate(any()))
                .thenReturn(new ArrayList<>());

        assertThrows(
                RuntimeException.class,
                () -> ensayosService.findByDate(LocalDateTime.now())
        );
    }

    @Test
    void findBySectionExitoso(){

        Integrantes integrante = new Integrantes();
        integrante.setId(1);
        integrante.setName("Juan");

        Ensayos ensayo = new Ensayos();
        ensayo.setIntegrante(integrante);

        when(ensayosRepository.findBySection("Bronces"))
                .thenReturn(List.of(ensayo));

        List<EnsayosResponseDTO> response =
                ensayosService.findBySection("Bronces");

        assertEquals(1, response.size());
    }

    @Test
    void findBySectionVacio(){

        when(ensayosRepository.findBySection("Bronces"))
                .thenReturn(new ArrayList<>());

        assertThrows(
                RuntimeException.class,
                () -> ensayosService.findBySection("Bronces")
        );
    }

    @Test
    void findByAssistanceExitoso(){

        Integrantes integrante = new Integrantes();
        integrante.setId(1);
        integrante.setName("Juan");

        Ensayos ensayo = new Ensayos();
        ensayo.setIntegrante(integrante);

        when(ensayosRepository.findByAssistance("Presente"))
                .thenReturn(List.of(ensayo));

        List<EnsayosResponseDTO> response =
                ensayosService.findByAssistance("Presente");

        assertEquals(1, response.size());
    }

    @Test
    void findByAssistanceVacio(){

        when(ensayosRepository.findByAssistance("Presente"))
                .thenReturn(new ArrayList<>());

        assertThrows(
                RuntimeException.class,
                () -> ensayosService.findByAssistance("Presente")
        );
    }
    @Test
    void findAll(){

        Integrantes integrante = new Integrantes();

        integrante.setId(1);
        integrante.setName("Juan");

        Ensayos ensayo = new Ensayos();

        ensayo.setId(1);
        ensayo.setIntegrante(integrante);

        when(ensayosRepository.findAll())
                .thenReturn(List.of(ensayo));

        List<EnsayosResponseDTO> response =
                ensayosService.findAll();

        assertNotNull(response);

        assertEquals(1, response.size());
    }

    @Test
    void saveEnsayoExitoso(){

        EnsayosRequestDTO dto =
                new EnsayosRequestDTO();

        dto.setDate(LocalDateTime.of(2026,1,1,5,3));
        dto.setSection("Bronces");

        Integrantes integrante =
                new Integrantes();

        integrante.setId(1);
        integrante.setName("Juan");

        when(ensayosRepository.findByDate(
                dto.getDate()))
                .thenReturn(new ArrayList<>());

        when(integrantesRepository.findAll())
                .thenReturn(List.of(integrante));

        when(ensayosRepository.save(any(Ensayos.class)))
                .thenAnswer(i -> {

                    Ensayos ensayo =
                            i.getArgument(0);

                    ensayo.setId(1);

                    return ensayo;
                });

        List<EnsayosResponseDTO> response =
                ensayosService.saveEnsayo(dto);

        assertNotNull(response);

        assertEquals(1, response.size());

        verify(ensayosRepository)
                .save(any(Ensayos.class));
    }

    @Test
    void saveEnsayoFechaRepetida(){

        EnsayosRequestDTO dto =
                new EnsayosRequestDTO();

        dto.setDate(LocalDateTime.of(2026,1,1,6,0,9));

        Ensayos ensayo =
                new Ensayos();

        when(ensayosRepository.findByDate(
                dto.getDate()))
                .thenReturn(List.of(ensayo));

        assertThrows(
                RuntimeException.class,
                () -> ensayosService.saveEnsayo(dto)
        );
    }
    @Test
    void deleteEnsayoExitoso(){

        Ensayos ensayo =
                new Ensayos();

        ensayo.setDate(
                LocalDateTime.of(2026,1,1,0,0)
        );

        when(ensayosRepository.findById(1))
                .thenReturn(Optional.of(ensayo));

        when(ensayosRepository.findByDate(
                LocalDateTime.of(2026,1,1,0,0)))
                .thenReturn(List.of(ensayo));

        ensayosService.deleteEnsayo(1);

        verify(ensayosRepository)
                .delete(ensayo);
    }

    @Test
    void deleteEnsayoNoExiste(){

        when(ensayosRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> ensayosService.deleteEnsayo(1)
        );
    }

    @Test
    void editEnsayoExitoso(){

        EnsayosRequestDTO dto =
                new EnsayosRequestDTO();

        dto.setDate(
                LocalDateTime.of(2026,1,1,1,0,4)
        );

        dto.setSection("Bronces");


        Integrantes integrante =
                new Integrantes();

        integrante.setId(1);

        integrante.setName("Juan");

        Ensayos ensayo =
                new Ensayos();

        ensayo.setId(1);

        ensayo.setDate(
                LocalDateTime.of(2025,1,1,4,0,0)
        );

        ensayo.setSection("Vieja");

        ensayo.setAssistance("Pendiente");

        ensayo.setIntegrante(integrante);

        when(ensayosRepository.findById(1))
                .thenReturn(Optional.of(ensayo));

        when(ensayosRepository.findByDate(
                LocalDateTime.of(2025,1,1,9,0,0)))
                .thenReturn(List.of(ensayo));

        when(ensayosRepository.findByDate(
                LocalDateTime.of(2026,1,1,0,0,0)))
                .thenReturn(new ArrayList<>());

        when(ensayosRepository.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        List<EnsayosResponseDTO> response =
                ensayosService.editEnsayo(
                        1,
                        dto
                );

        assertNotNull(response);

        assertEquals(1, response.size());

        assertEquals(
                "Bronces",
                response.get(0).getSection()
        );
    }

    @Test
    void editEnsayoNoExiste(){

        EnsayosRequestDTO dto =
                new EnsayosRequestDTO();

        when(ensayosRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> ensayosService.editEnsayo(
                        1,
                        dto
                )
        );
    }

    @Test
    void editEnsayoFechaDuplicada(){

        EnsayosRequestDTO dto =
                new EnsayosRequestDTO();

        dto.setDate(
                LocalDateTime.of(2026,1,1,8,0,0)
        );

        Ensayos ensayoOriginal =
                new Ensayos();

        ensayoOriginal.setDate(
                LocalDateTime.of(2025,1,1,9,0,0)
        );

        when(ensayosRepository.findById(1))
                .thenReturn(
                        Optional.of(ensayoOriginal)
                );

        when(ensayosRepository.findByDate(
                LocalDateTime.of(2026,1,1,9,0,0)))
                .thenReturn(
                        List.of(new Ensayos())
                );

        assertThrows(
                RuntimeException.class,
                () -> ensayosService.editEnsayo(
                        1,
                        dto
                )
        );
    }

}

