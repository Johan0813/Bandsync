package BandSync;

import BandSync.Model.Integrantes.Integrantes;
import BandSync.Repository.Integrantes.IntegrantesRepository;
import BandSync.Service.Integrantes.CustomIntegrantesDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomIntegrantesDetailsServiceTest {

    @Mock
    private IntegrantesRepository repositoryInt;

    @InjectMocks
    private CustomIntegrantesDetailsService service;

    @Test
    void loadUserByUsernameExitoso(){

        Integrantes integrante = new Integrantes();

        integrante.setId(1);
        integrante.setName("Juan");
        integrante.setEmail("juan@bco.or.cr");
        integrante.setPassword("123456");
        integrante.setType("ADMIN");

        when(repositoryInt.findByEmail(
                "juan@bco.or.cr"))
                .thenReturn(Optional.of(integrante));

        UserDetails user =
                service.loadUserByUsername(
                        "juan@bco.or.cr"
                );

        assertNotNull(user);

        assertEquals(
                "juan@bco.or.cr",
                user.getUsername()
        );

        assertEquals(
                "123456",
                user.getPassword()
        );
    }

    @Test
    void loadUserByUsernameNoExiste(){

        when(repositoryInt.findByEmail(
                "noexiste@bco.or.cr"))
                .thenReturn(Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> service.loadUserByUsername(
                        "noexiste@bco.or.cr"
                )
        );
    }
}