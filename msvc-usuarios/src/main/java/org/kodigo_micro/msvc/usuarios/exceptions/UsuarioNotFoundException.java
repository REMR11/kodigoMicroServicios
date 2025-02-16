package org.kodigo_micro.msvc.usuarios.exceptions;

public class UsuarioNotFoundException extends RuntimeException{
    private final long id;
    private final String message;
    public UsuarioNotFoundException(String message, Long id) {
        super(String.format("Error en la entidad '%s': la fecha de inicio (%s) debe ser anterior a la fecha de finalización.",
                message, id));
        this.id = id;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
