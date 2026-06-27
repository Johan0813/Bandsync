package BandSync;

import BandSync.Model.Instrumentos.Instrumentos;
import BandSync.Model.Integrantes.Integrantes;
import BandSync.Model.Integrantes.IntegrantesRequestDTO;
import BandSync.Model.Integrantes.IntegrantesResponseDTO;
import BandSync.Repository.Instrumentos.InstrumentosRepository;
import BandSync.Repository.Integrantes.IntegrantesRepository;
import BandSync.Service.Integrantes.IntegrantesService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class IntegrantesServiceTest {

    @Mock
    private IntegrantesRepository repositoryInt;

    @Mock
    private InstrumentosRepository repositoryInst;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private IntegrantesService service;

    @Test
    void convertirIntegrantesDTOAdmin(){

        Integrantes admin = new Integrantes();

        admin.setId(1);
        admin.setName("Admin");
        admin.setEmail("admin@bco.or.cr");
        admin.setAge(30);
        admin.setType("ADMIN");
        admin.setSection("General");

        IntegrantesResponseDTO response =
                service.convertirIntegrantesDTO(admin);

        assertNotNull(response);

        assertEquals(
                "Administrador",
                response.getInstrument()
        );
    }

    @Test
    void convertirIntegrantesDTOIntegrante(){

        Instrumentos instrumento = new Instrumentos();

        instrumento.setId(1);
        instrumento.setName("Trompeta");

        Integrantes integrante = new Integrantes();

        integrante.setInstrument(instrumento);

        IntegrantesResponseDTO response =
                service.convertirIntegrantesDTO(integrante);

        assertNotNull(response);

        assertEquals(
                "Trompeta",
                response.getInstrument()
        );
    }

    @Test
    void saveIntegranteExitoso(){

        IntegrantesRequestDTO dto =
                new IntegrantesRequestDTO();

        dto.setName("Juan");
        dto.setEmail("juan@bco.or.cr");
        dto.setPassword("Admin123*");
        dto.setAge(20);
        dto.setType("INTEGRANTE");
        dto.setInstrumentId(1);
        dto.setSection("Bronces");

        Instrumentos instrumento =
                new Instrumentos();

        instrumento.setId(1);
        instrumento.setName("Trompeta");
        instrumento.setQuantity(5);

        Integrantes integrante =
                new Integrantes(
                        dto.getName(),
                        dto.getEmail(),
                        "password",
                        dto.getAge(),
                        dto.getType(),
                        instrumento,
                        dto.getSection()
                );

        when(repositoryInt.findByEmail(
                dto.getEmail()))

                .thenReturn(Optional.empty());

        when(repositoryInst.findById(1))

                .thenReturn(
                        Optional.of(instrumento)
                );

        when(passwordEncoder.encode(
                dto.getPassword()))

                .thenReturn("password");

        when(repositoryInt.save(any(
                Integrantes.class)))

                .thenReturn(integrante);

        IntegrantesResponseDTO response =
                service.saveIntegrante(dto);

        assertNotNull(response);
    }

    @Test
    void saveIntegranteCorreoExistente(){

        IntegrantesRequestDTO dto =
                new IntegrantesRequestDTO();

        dto.setEmail("juan@bco.or.cr");

        when(repositoryInt.findByEmail(
                dto.getEmail()))

                .thenReturn(
                        Optional.of(
                                new Integrantes()
                        )
                );

        assertThrows(
                RuntimeException.class,
                () -> service.saveIntegrante(dto)
        );
    }

    @Test
    void saveIntegranteSinInstrumento(){

        IntegrantesRequestDTO dto =
                new IntegrantesRequestDTO();

        dto.setType("INTEGRANTE");

        when(repositoryInt.findByEmail(
                anyString()))

                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> service.saveIntegrante(dto)
        );
    }

    @Test
    void saveIntegranteInstrumentoNoExiste(){

        IntegrantesRequestDTO dto =
                new IntegrantesRequestDTO();

        dto.setType("INTEGRANTE");
        dto.setInstrumentId(1);

        when(repositoryInt.findByEmail(
                anyString()))

                .thenReturn(Optional.empty());

        when(repositoryInst.findById(1))

                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> service.saveIntegrante(dto)
        );
    }

    @Test
    void saveIntegranteSinCantidad(){

        IntegrantesRequestDTO dto =
                new IntegrantesRequestDTO();

        dto.setType("INTEGRANTE");
        dto.setInstrumentId(1);

        Instrumentos instrumento =
                new Instrumentos();

        instrumento.setId(1);
        instrumento.setQuantity(0);

        when(repositoryInt.findByEmail(
                anyString()))

                .thenReturn(Optional.empty());

        when(repositoryInst.findById(1))

                .thenReturn(
                        Optional.of(instrumento)
                );

        assertThrows(
                RuntimeException.class,
                () -> service.saveIntegrante(dto)
        );
    }

    @Test
    void findAllExitoso(){

        List<Integrantes> integrantes =
                new ArrayList<>();

        Integrantes integrante =
                new Integrantes();

        integrante.setType("ADMIN");

        integrantes.add(integrante);

        when(repositoryInt.findAll())

                .thenReturn(integrantes);

        List<IntegrantesResponseDTO> response =
                service.findAll();

        assertFalse(response.isEmpty());

        assertEquals(
                1,
                response.size()
        );
    }
    @Test
    void deleteIntegranteExitoso(){

        Instrumentos instrumento =
                new Instrumentos();

        instrumento.setQuantity(2);

        Integrantes integrante =
                new Integrantes();

        integrante.setInstrument(
                instrumento
        );

        when(repositoryInt.findById(1))

                .thenReturn(
                        Optional.of(integrante)
                );

        service.deleteIntegrante(1);

        verify(repositoryInst)
                .save(instrumento);

        verify(repositoryInt)
                .deleteById(1);
    }

    @Test
    void deleteIntegranteNoExiste(){

        when(repositoryInt.findById(1))

                .thenReturn(
                        Optional.empty()
                );

        assertThrows(

                RuntimeException.class,

                () -> service.deleteIntegrante(1)
        );
    }

    @Test
    void editIntegranteExitoso(){

        Instrumentos actual =
                new Instrumentos();

        actual.setId(1);

        actual.setQuantity(5);

        Instrumentos nuevo =
                new Instrumentos();

        nuevo.setId(2);

        nuevo.setQuantity(5);

        Integrantes integrante =
                new Integrantes();

        integrante.setEmail(
                "viejo@bco.or.cr"
        );

        integrante.setType(
                "INTEGRANTE"
        );

        integrante.setInstrument(
                actual
        );

        IntegrantesRequestDTO dto =
                new IntegrantesRequestDTO();

        dto.setName("Juan");

        dto.setEmail(
                "nuevo@bco.or.cr"
        );

        dto.setAge(20);

        dto.setSection(
                "Bronces"
        );

        dto.setInstrumentId(2);

        when(repositoryInt.findById(1))

                .thenReturn(
                        Optional.of(integrante)
                );

        when(repositoryInt.findByEmail(
                "nuevo@bco.or.cr"))

                .thenReturn(
                        Optional.empty()
                );

        when(repositoryInst.findById(2))

                .thenReturn(
                        Optional.of(nuevo)
                );

        when(repositoryInt.save(any(
                Integrantes.class)))

                .thenReturn(integrante);

        IntegrantesResponseDTO response =

                service.editIntegrante(
                        1,
                        dto
                );

        assertNotNull(response);
    }

    @Test
    void editIntegranteNoExiste(){

        IntegrantesRequestDTO dto =
                new IntegrantesRequestDTO();

        when(repositoryInt.findById(1))

                .thenReturn(
                        Optional.empty()
                );

        assertThrows(

                RuntimeException.class,

                () -> service.editIntegrante(
                        1,
                        dto
                )
        );
    }

    @Test
    void editIntegranteCorreoExistente(){

        Integrantes existente =
                new Integrantes();

        existente.setEmail(
                "a@bco.or.cr"
        );

        existente.setType(
                "INTEGRANTE"
        );

        existente.setInstrument(
                new Instrumentos()
        );

        IntegrantesRequestDTO dto =
                new IntegrantesRequestDTO();

        dto.setEmail(
                "b@bco.or.cr"
        );

        when(repositoryInt.findById(1))

                .thenReturn(
                        Optional.of(existente)
                );

        when(repositoryInt.findByEmail(
                "b@bco.or.cr"))

                .thenReturn(
                        Optional.of(
                                new Integrantes()
                        )
                );

        assertThrows(

                RuntimeException.class,

                () -> service.editIntegrante(
                        1,
                        dto
                )
        );
    }

    @Test
    void editIntegranteSinInstrumento(){

        Integrantes integrante =
                new Integrantes();

        integrante.setEmail(
                "a@bco.or.cr"
        );

        integrante.setType(
                "INTEGRANTE"
        );

        IntegrantesRequestDTO dto =
                new IntegrantesRequestDTO();

        dto.setEmail(
                "a@bco.or.cr"
        );

        when(repositoryInt.findById(1))

                .thenReturn(
                        Optional.of(integrante)
                );

        assertThrows(

                RuntimeException.class,

                () -> service.editIntegrante(
                        1,
                        dto
                )
        );
    }

    @Test
    void findByIdExitoso(){

        Integrantes integrante =
                new Integrantes();

        integrante.setId(1);

        integrante.setType("ADMIN");

        when(repositoryInt.findById(1))

                .thenReturn(
                        Optional.of(integrante)
                );

        IntegrantesResponseDTO response =

                service.findById(1);

        assertNotNull(response);
    }

    @Test
    void findByIdNoExiste(){

        when(repositoryInt.findById(1))

                .thenReturn(
                        Optional.empty()
                );

        assertThrows(

                RuntimeException.class,

                () -> service.findById(1)
        );
    }
    @Test
    void findByEmailExitoso(){

        Integrantes integrante =
                new Integrantes();

        integrante.setType("ADMIN");

        when(repositoryInt.findByEmail(
                "admin@bco.or.cr"))

                .thenReturn(
                        Optional.of(integrante)
                );

        IntegrantesResponseDTO response =

                service.findByEmail(
                        "admin@bco.or.cr"
                );

        assertNotNull(response);
    }

    @Test
    void findByEmailNoExiste(){

        when(repositoryInt.findByEmail(
                "admin@bco.or.cr"))

                .thenReturn(
                        Optional.empty()
                );

        assertThrows(

                RuntimeException.class,

                () -> service.findByEmail(
                        "admin@bco.or.cr"
                )
        );
    }

    @Test
    void findByTypeExitoso(){

        List<Integrantes> integrantes =
                new ArrayList<>();

        Integrantes integrante =
                new Integrantes();

        integrante.setType(
                "ADMIN"
        );

        integrantes.add(integrante);

        when(repositoryInt.findByType(
                "ADMIN"))

                .thenReturn(integrantes);

        List<IntegrantesResponseDTO> response =

                service.findByType(
                        "ADMIN"
                );

        assertFalse(response.isEmpty());
    }

    @Test
    void findByTypeNoExiste(){

        when(repositoryInt.findByType(
                "ADMIN"))

                .thenReturn(
                        new ArrayList<>()
                );

        assertThrows(

                RuntimeException.class,

                () -> service.findByType(
                        "ADMIN"
                )
        );
    }

    @Test
    void findBySectionExitoso(){

        List<Integrantes> integrantes =
                new ArrayList<>();

        Integrantes integrante =
                new Integrantes();

        integrante.setType(
                "ADMIN"
        );

        integrantes.add(integrante);

        when(repositoryInt.findBySection(
                "Bronces"))

                .thenReturn(integrantes);

        List<IntegrantesResponseDTO> response =

                service.findBySection(
                        "Bronces"
                );

        assertFalse(response.isEmpty());
    }

    @Test
    void findBySectionNoExiste(){

        when(repositoryInt.findBySection(
                "Bronces"))

                .thenReturn(
                        new ArrayList<>()
                );

        assertThrows(

                RuntimeException.class,

                () -> service.findBySection(
                        "Bronces"
                )
        );
    }

    @Test
    void findByNameExitoso(){

        List<Integrantes> integrantes =
                new ArrayList<>();

        Integrantes integrante =
                new Integrantes();

        integrante.setType(
                "ADMIN"
        );

        integrantes.add(integrante);

        when(repositoryInt.findByName(
                "Juan"))

                .thenReturn(integrantes);

        List<IntegrantesResponseDTO> response =

                service.findByName(
                        "Juan"
                );

        assertFalse(response.isEmpty());
    }

    @Test
    void findByNameNoExiste(){

        when(repositoryInt.findByName(
                "Juan"))

                .thenReturn(
                        new ArrayList<>()
                );

        assertThrows(

                RuntimeException.class,

                () -> service.findByName(
                        "Juan"
                )
        );
    }

    @Test
    void findAllByOrderByNameAscExitoso(){

        List<Integrantes> integrantes =
                new ArrayList<>();

        Integrantes integrante =
                new Integrantes();

        integrante.setType(
                "ADMIN"
        );

        integrantes.add(integrante);

        when(repositoryInt.findAllByOrderByNameAsc())

                .thenReturn(integrantes);

        List<IntegrantesResponseDTO> response =

                service.findAllByOrderByNameAsc();

        assertFalse(response.isEmpty());
    }

    @Test
    void findAllByOrderByNameAscNoExiste(){

        when(repositoryInt.findAllByOrderByNameAsc())

                .thenReturn(
                        new ArrayList<>()
                );

        assertThrows(

                RuntimeException.class,

                () -> service.findAllByOrderByNameAsc()
        );
    }
}
