package org.kodigo_micro.msvc.cursos.models.dtos;

import java.time.LocalDate;

/***
 * Interfaz destinada a utilizarce en metodos para verificar si una fecha es correcta
 */
public interface CursoFechaDTO {
    LocalDate inicio();
    LocalDate finalizacion();
}
