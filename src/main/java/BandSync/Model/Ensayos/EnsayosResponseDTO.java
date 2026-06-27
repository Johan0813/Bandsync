package BandSync.Model.Ensayos;

import BandSync.Model.Integrantes.Integrantes;

import java.time.LocalDate;

public class EnsayosResponseDTO {
    private Integer id;
    private LocalDate date;
    private String section;
    private Integrantes integrante;
    private String assistance;
}
