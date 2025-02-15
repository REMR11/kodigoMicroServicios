package org.kodigo_micro.msvc.cursos.exceptions;

import java.time.LocalDate;

public class FechaInvalidaException extends RuntimeException {
    private final LocalDate fechaInicio;
    private final LocalDate fechaFin;
    private final String entidad;

    public FechaInvalidaException(LocalDate fechaInicio, LocalDate fechaFin, String entidad) {
        super(String.format("Error en la entidad '%s': la fecha de inicio (%s) debe ser anterior a la fecha de finalización (%s).",
                entidad, fechaInicio, fechaFin));
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.entidad = entidad;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public String getEntidad() {
        return entidad;
    }
}

