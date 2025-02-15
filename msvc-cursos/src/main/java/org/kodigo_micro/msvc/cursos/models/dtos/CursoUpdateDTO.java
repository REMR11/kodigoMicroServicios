package org.kodigo_micro.msvc.cursos.models.dtos;

import java.time.LocalDate;

public record CursoUpdateDTO(
        String nombre,
        LocalDate inicio,
        LocalDate finalizacion,
        Double notaMinima,
        boolean state
) implements CursoFechaDTO {}
