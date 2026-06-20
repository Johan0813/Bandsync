package BandSync.Service.Integrantes;

import BandSync.Model.Integrantes.Integrantes;
import BandSync.Repository.Integrantes.IntegrantesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomIntegrantesDetailsService {
    @Autowired
    private IntegrantesRepository repositoryInt;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Integrantes integrante = this.repositoryInt.findByEmail(email);

        if(integrante == null){

            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        return User.builder()

                .username(integrante.getEmail())

                .password(integrante.getPassword())

                .roles(integrante.getType())

                .build();
    }
}

