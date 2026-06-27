package BandSync.Service.Integrantes;

import BandSync.Model.Integrantes.Integrantes;
import BandSync.Repository.Integrantes.IntegrantesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomIntegrantesDetailsService implements UserDetailsService {

    @Autowired
    private IntegrantesRepository repositoryInt;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Integrantes> optional = this.repositoryInt.findByEmail(email);

        if(optional.isEmpty()){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        Integrantes integrante = optional.get();

        return User.builder()
                .username(integrante.getEmail())
                .password(integrante.getPassword())
                .roles(integrante.getType())
                .build();
    }
}