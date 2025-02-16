package org.kodigo_micro.msvc.cursos.models.dtos;

import java.time.LocalDate;

/***
 * Record destinado a utilizarce en la creacion de un nuevo curso
 * @param nombre
 * @param inicio
 * @param finalizacion
 * @param notaMinima
 */
public record CursoDTO(
        String nombre,
        LocalDate inicio,
        LocalDate finalizacion,
        Double notaMinima
) implements CursoFechaDTO {}

