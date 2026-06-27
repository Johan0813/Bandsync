package BandSync;

import BandSync.Model.Instrumentos.Instrumentos;
import BandSync.Model.Instrumentos.InstrumentosRequestDTO;
import BandSync.Model.Instrumentos.InstrumentosResponseDTO;
import BandSync.Repository.Instrumentos.InstrumentosRepository;
import BandSync.Service.Instrumentos.InstrumentosService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InstrumentosServiceTest {

    @Mock
    private InstrumentosRepository instrumentosRepository;

    @InjectMocks
    private InstrumentosService instrumentosService;

    @Test
    void convertInstrumentoDTO() {

        Instrumentos instrumento =
                new Instrumentos();

        instrumento.setId(1);
        instrumento.setName("Trompeta");
        instrumento.setQuantity(10);

        InstrumentosResponseDTO dto =
                instrumentosService.convertInstrumentoDTO(instrumento);

        assertEquals(1, dto.getId());
        assertEquals("Trompeta", dto.getName());
        assertEquals(10, dto.getQuantity());
    }

    @Test
    void findByIdExitoso() {

        Instrumentos instrumento =
                new Instrumentos();

        instrumento.setId(1);
        instrumento.setName("Trompeta");

        when(instrumentosRepository.findById(1))
                .thenReturn(Optional.of(instrumento));

        InstrumentosResponseDTO response =
                instrumentosService.findById(1);

        assertNotNull(response);
    }

    @Test
    void findByIdNoExiste() {

        when(instrumentosRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> instrumentosService.findById(1)
        );
    }

    @Test
    void findAll() {

        Instrumentos instrumento =
                new Instrumentos();

        instrumento.setId(1);
        instrumento.setName("Trompeta");

        when(instrumentosRepository.findAll())
                .thenReturn(List.of(instrumento));

        List<InstrumentosResponseDTO> response =
                instrumentosService.findAll();

        assertEquals(1, response.size());
    }
    @Test
    void saveInstrumentoNuevo(){

        InstrumentosRequestDTO dto =
                new InstrumentosRequestDTO();

        dto.setName("Trompeta");
        dto.setQuantity(5);

        when(instrumentosRepository.findByName("Trompeta"))
                .thenReturn(new ArrayList<>());

        when(instrumentosRepository.save(any()))
                .thenAnswer(i -> {

                    Instrumentos instrumento =
                            i.getArgument(0);

                    instrumento.setId(1);

                    return instrumento;
                });

        InstrumentosResponseDTO response =
                instrumentosService.saveInstrumento(dto);

        assertNotNull(response);

        assertEquals(
                "Trompeta",
                response.getName()
        );
    }

    @Test
    void saveInstrumentoExistente(){

        Instrumentos existente =
                new Instrumentos();

        existente.setId(1);
        existente.setName("Trompeta");
        existente.setQuantity(10);

        InstrumentosRequestDTO dto =
                new InstrumentosRequestDTO();

        dto.setName("Trompeta");
        dto.setQuantity(5);

        when(instrumentosRepository.findByName("Trompeta"))
                .thenReturn(List.of(existente));

        when(instrumentosRepository.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        InstrumentosResponseDTO response =
                instrumentosService.saveInstrumento(dto);

        assertEquals(
                15,
                response.getQuantity()
        );
    }

    @Test
    void saveInstrumentoCantidadInvalida(){

        InstrumentosRequestDTO dto =
                new InstrumentosRequestDTO();

        dto.setName("Trompeta");
        dto.setQuantity(0);

        assertThrows(
                RuntimeException.class,
                () -> instrumentosService.saveInstrumento(dto)
        );
    }
    @Test
    void editInstrumentoExitoso(){

        Instrumentos instrumento =
                new Instrumentos();

        instrumento.setId(1);
        instrumento.setQuantity(10);

        InstrumentosRequestDTO dto =
                new InstrumentosRequestDTO();

        dto.setQuantity(20);

        when(instrumentosRepository.findById(1))
                .thenReturn(Optional.of(instrumento));

        when(instrumentosRepository.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        InstrumentosResponseDTO response =
                instrumentosService.editInstrumento(1,dto);

        assertEquals(
                20,
                response.getQuantity()
        );
    }

    @Test
    void editInstrumentoNoExiste(){

        InstrumentosRequestDTO dto =
                new InstrumentosRequestDTO();

        when(instrumentosRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> instrumentosService.editInstrumento(1,dto)
        );
    }

    @Test
    void deleteInstrumentoExitoso(){

        Instrumentos instrumento =
                new Instrumentos();

        instrumento.setId(1);
        instrumento.setQuantity(20);

        when(instrumentosRepository.findById(1))
                .thenReturn(Optional.of(instrumento));

        when(instrumentosRepository.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        InstrumentosResponseDTO response =
                instrumentosService.deleteInstrumento(1,5);

        assertEquals(
                15,
                response.getQuantity()
        );
    }

    @Test
    void deleteInstrumentoNoExiste(){

        when(instrumentosRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> instrumentosService.deleteInstrumento(1,5)
        );
    }

    @Test
    void deleteInstrumentoCantidadMayor(){

        Instrumentos instrumento =
                new Instrumentos();

        instrumento.setQuantity(5);

        when(instrumentosRepository.findById(1))
                .thenReturn(Optional.of(instrumento));

        assertThrows(
                RuntimeException.class,
                () -> instrumentosService.deleteInstrumento(1,10)
        );
    }

}
