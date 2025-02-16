package org.kodigo_micro.msvc.cursos.exceptions;

public class NotaNegativaException extends RuntimeException {
    private final double notaMinima;
    private final String nombre;

    public NotaNegativaException(Double notaMinima, String nombre) {
        super(String.format("Error en el curso '%s': La nota minima no deberia ser negativa o igual a cero, se encontro una nota minina de '%s'",
        nombre, notaMinima));
        this.notaMinima = notaMinima;
        this.nombre = nombre;
    }

    public double getNotaMinima() {
        return notaMinima;
    }

    public String getNombre() {
        return nombre;
    }
}
