package BandSync.Service.Integrantes;

import BandSync.Model.Integrantes.Integrantes;
import BandSync.Model.Integrantes.IntegrantesResponseDTO;
import BandSync.Model.Login.LoginDTO;
import BandSync.Repository.Integrantes.IntegrantesRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.stereotype.Service;

@Service

public class AuthService {

    @Autowired
    private IntegrantesRepository repositoryInt;

    @Autowired
    private AuthenticationManager authenticationManager;

    public IntegrantesResponseDTO login(LoginDTO dto){

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());

        authenticationManager.authenticate(authToken);

        Integrantes integrante = this.repositoryInt.findByEmail(dto.getEmail());

        if(integrante == null){
            throw new RuntimeException("Usuario no encontrado");
        }

        if(integrante.getInstrument() == null){

            return new IntegrantesResponseDTO(
                    integrante.getId(),
                    integrante.getName(),
                    integrante.getEmail(),
                    integrante.getAge(),
                    integrante.getType(),
                    "Administrador",
                    0,
                    integrante.getSection()
            );
        }

        return new IntegrantesResponseDTO(
                integrante.getId(),
                integrante.getName(),
                integrante.getEmail(),
                integrante.getAge(),
                integrante.getType(),
                integrante.getInstrument().getName(),
                integrante.getInstrument().getId(),
                integrante.getSection()
        );
    }
}