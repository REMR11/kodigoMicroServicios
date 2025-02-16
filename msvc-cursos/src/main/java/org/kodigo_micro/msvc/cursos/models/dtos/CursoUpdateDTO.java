package org.kodigo_micro.msvc.cursos.models.dtos;

import java.time.LocalDate;

/***
 * Recprd que implementa la interfaz CursoFechaDTO, creada para utilizarce en metodo de actualizacion de curso
 * @param nombre
 * @param inicio
 * @param finalizacion
 * @param notaMinima
 * @param state
 */
public record CursoUpdateDTO(
        String nombre,
        LocalDate inicio,
        LocalDate finalizacion,
        Double notaMinima,
        boolean state
) implements CursoFechaDTO {}
