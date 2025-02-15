package org.kodigo_micro.msvc.cursos.exceptions;

import java.time.LocalDate;

public class DuracionInvalidaException extends RuntimeException {
    private final LocalDate fechaInicio;
    private final LocalDate fechaFin;
    private final long diasDuracion;
    private final String entidad;

    public DuracionInvalidaException(LocalDate fechaInicio, LocalDate fechaFin, long diasDuracion, String entidad) {
        super(String.format("Error en la entidad '%s': la duración mínima permitida es de 7 días, pero se encontró un periodo de %d días (desde %s hasta %s).",
                entidad, diasDuracion, fechaInicio, fechaFin));
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.diasDuracion = diasDuracion;
        this.entidad = entidad;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public long getDiasDuracion() {
        return diasDuracion;
    }

    public String getEntidad() {
        return entidad;
    }
}
