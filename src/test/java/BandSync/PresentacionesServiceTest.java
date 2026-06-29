package BandSync;

import BandSync.Model.Integrantes.Integrantes;
import BandSync.Model.Presentaciones.Presentaciones;
import BandSync.Model.Presentaciones.PresentacionesRequestDTO;
import BandSync.Model.Presentaciones.PresentacionesResponseDTO;
import BandSync.Repository.Integrantes.IntegrantesRepository;
import BandSync.Repository.Presentaciones.PresentacionesRepository;
import BandSync.Service.Presentaciones.PresentacionesService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PresentacionesServiceTest {

    @Mock
    private PresentacionesRepository presentacionesRepository;

    @Mock
    private IntegrantesRepository integrantesRepository;

    @InjectMocks
    private PresentacionesService presentacionesService;

    @Test
    void convertirPresentacionesDTO() {

        Integrantes integrante = new Integrantes();
        integrante.setId(1);
        integrante.setName("Juan");

        Presentaciones presentacion = new Presentaciones();
        presentacion.setId(1);
        presentacion.setDate(LocalDateTime.now());
        presentacion.setLocation("Puntarenas");
        presentacion.setAssistance("Presente");
        presentacion.setIntegrante(integrante);

        PresentacionesResponseDTO dto =
                presentacionesService.convertirPresentacionesDTO(presentacion);

        assertEquals("Juan", dto.getIntegrante());
        assertEquals(1, dto.getIntegranteId());
    }

    @Test
    void findByIdExitoso() {

        Integrantes integrante = new Integrantes();
        integrante.setId(1);
        integrante.setName("Juan");

        Presentaciones presentacion = new Presentaciones();
        presentacion.setId(1);
        presentacion.setIntegrante(integrante);

        when(presentacionesRepository.findById(1))
                .thenReturn(Optional.of(presentacion));

        PresentacionesResponseDTO response =
                presentacionesService.findById(1);

        assertNotNull(response);
    }

    @Test
    void findByIdNoExiste() {

        when(presentacionesRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> presentacionesService.findById(1)
        );
    }

    @Test
    void findAll() {

        Integrantes integrante = new Integrantes();
        integrante.setId(1);
        integrante.setName("Juan");

        Presentaciones presentacion = new Presentaciones();
        presentacion.setIntegrante(integrante);

        when(presentacionesRepository.findAll())
                .thenReturn(List.of(presentacion));

        List<PresentacionesResponseDTO> response =
                presentacionesService.findAll();

        assertEquals(1, response.size());
    }
    @Test
    void savePresentationExitoso(){

        PresentacionesRequestDTO dto =
                new PresentacionesRequestDTO();

        dto.setDate(LocalDateTime.of(2026,1,1,9,0));
        dto.setLocation("Puntarenas");
        dto.setAsisstance("Pendiente");

        Integrantes integrante =
                new Integrantes();

        integrante.setId(1);
        integrante.setName("Juan");

        when(presentacionesRepository.findByDate(dto.getDate()))
                .thenReturn(new ArrayList<>());

        when(integrantesRepository.findAll())
                .thenReturn(List.of(integrante));

        when(presentacionesRepository.save(any()))
                .thenAnswer(i -> {

                    Presentaciones p =
                            i.getArgument(0);

                    p.setId(1);

                    return p;
                });

        List<PresentacionesResponseDTO> response =
                presentacionesService.savePresentation(dto);

        assertEquals(1,response.size());
    }

    @Test
    void savePresentationFechaExistente(){

        PresentacionesRequestDTO dto =
                new PresentacionesRequestDTO();

        dto.setDate(LocalDateTime.now());

        when(presentacionesRepository.findByDate(dto.getDate()))
                .thenReturn(List.of(new Presentaciones()));

        assertThrows(
                RuntimeException.class,
                () -> presentacionesService.savePresentation(dto)
        );
    }

    @Test
    void savePresentationSinIntegrantes(){

        PresentacionesRequestDTO dto =
                new PresentacionesRequestDTO();

        dto.setDate(LocalDateTime.now());

        when(presentacionesRepository.findByDate(dto.getDate()))
                .thenReturn(new ArrayList<>());

        when(integrantesRepository.findAll())
                .thenReturn(new ArrayList<>());

        assertThrows(
                RuntimeException.class,
                () -> presentacionesService.savePresentation(dto)
        );
    }

    @Test
    void deletePresentationExitoso(){

        Presentaciones presentacion =
                new Presentaciones();

        presentacion.setDate(LocalDateTime.now());

        when(presentacionesRepository.findById(1))
                .thenReturn(Optional.of(presentacion));

        when(presentacionesRepository.findByDate(any()))
                .thenReturn(List.of(presentacion));

        presentacionesService.deletePresentation(1);

        verify(presentacionesRepository)
                .delete(any());
    }
    @Test
    void deletePresentationNoExiste(){

        when(presentacionesRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> presentacionesService.deletePresentation(1)
        );
    }

    @Test
    void editPresentationExitoso(){

        LocalDateTime fecha =
                LocalDateTime.of(2026,1,1,9,2);

        Presentaciones presentacion =
                new Presentaciones();

        presentacion.setDate(fecha);

        PresentacionesRequestDTO dto =
                new PresentacionesRequestDTO();

        dto.setDate(fecha);
        dto.setLocation("San José");
        dto.setAsisstance("Presente");

        Integrantes integrante =
                new Integrantes();

        integrante.setId(1);
        integrante.setName("Juan");

        presentacion.setIntegrante(integrante);

        when(presentacionesRepository.findById(1))
                .thenReturn(Optional.of(presentacion));

        when(presentacionesRepository.findByDate(fecha))
                .thenReturn(List.of(presentacion));

        when(presentacionesRepository.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        List<PresentacionesResponseDTO> response =
                presentacionesService.editPresentation(1,dto);

        assertEquals(1,response.size());
    }

    @Test
    void editPresentationNoExiste(){

        PresentacionesRequestDTO dto =
                new PresentacionesRequestDTO();

        when(presentacionesRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> presentacionesService.editPresentation(1,dto)
        );
    }

    @Test
    void findByDateExitoso(){

        Integrantes integrante =
                new Integrantes();

        integrante.setId(1);
        integrante.setName("Juan");

        Presentaciones presentacion =
                new Presentaciones();

        presentacion.setIntegrante(integrante);

        when(presentacionesRepository.findByDate(any()))
                .thenReturn(List.of(presentacion));

        assertFalse(
                presentacionesService.findByDate(
                        LocalDateTime.of(2021,12,4,2,2)).isEmpty()
        );
    }

    @Test
    void findByLocationExitoso(){

        Integrantes integrante =
                new Integrantes();

        integrante.setId(1);
        integrante.setName("Juan");

        Presentaciones presentacion =
                new Presentaciones();

        presentacion.setIntegrante(integrante);

        when(presentacionesRepository.findByLocation("Puntarenas"))
                .thenReturn(List.of(presentacion));

        assertFalse(
                presentacionesService.findByLocation("Puntarenas")
                        .isEmpty()
        );
    }

}
