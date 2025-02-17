package org.kodigo_micro.msvc.usuarios.exceptions;

public class EmailDuplicateException extends RuntimeException {
    private final String email;
    public EmailDuplicateException(String email) {
        super(String.format("EL email '%s' ya es utilizado actualmente.", email));
        this.email = email;
    }
}
