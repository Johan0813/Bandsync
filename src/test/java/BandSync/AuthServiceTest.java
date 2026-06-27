package BandSync;

import BandSync.Model.Instrumentos.Instrumentos;
import BandSync.Model.Integrantes.Integrantes;
import BandSync.Model.Integrantes.IntegrantesResponseDTO;
import BandSync.Model.Login.LoginDTO;
import BandSync.Repository.Integrantes.IntegrantesRepository;
import BandSync.Service.Integrantes.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private IntegrantesRepository repositoryInt;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

        @Test
        void loginAdminExitoso(){

            LoginDTO dto = new LoginDTO();

            dto.setEmail("admin@bco.or.cr");

            dto.setPassword("1234");

            Integrantes admin = new Integrantes();

            admin.setId(1);

            admin.setName("Admin");

            admin.setEmail("admin@bco.or.cr");

            admin.setAge(30);

            admin.setType("ADMIN");

            admin.setSection("General");

            when(repositoryInt.findByEmail(
                    dto.getEmail()))

                    .thenReturn(Optional.of(admin));

            IntegrantesResponseDTO response =

                    authService.login(dto);

            assertNotNull(response);

            assertEquals(
                    "Administrador",
                    response.getInstrument()
            );

            verify(authenticationManager)

                    .authenticate(any(
                            UsernamePasswordAuthenticationToken.class
                    ));
        }

    @Test
    void loginIntegranteExitoso(){

        LoginDTO dto = new LoginDTO();

        dto.setEmail("juan@bco.or.cr");

        dto.setPassword("1234");

        Instrumentos instrumento =

                new Instrumentos();

        instrumento.setId(1);

        instrumento.setName("Trompeta");

        Integrantes integrante =

                new Integrantes();

        integrante.setId(1);

        integrante.setName("Juan");

        integrante.setEmail("juan@bco.or.cr");

        integrante.setAge(20);

        integrante.setType("INTEGRANTE");

        integrante.setSection("Bronces");

        integrante.setInstrument(instrumento);

        when(repositoryInt.findByEmail(
                dto.getEmail())).thenReturn(Optional.of(integrante));

        IntegrantesResponseDTO response =

                authService.login(dto);

        assertNotNull(response);

        assertEquals(
                "Trompeta",
                response.getInstrument()
        );

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void loginUsuarioNoExiste(){

        LoginDTO dto = new LoginDTO();

        dto.setEmail("admin@bco.or.cr");

        dto.setPassword("1234");

        when(repositoryInt.findByEmail(
                dto.getEmail()))

                .thenReturn(null);

        assertThrows(

                RuntimeException.class,

                () -> authService.login(dto)
        );
    }
}

