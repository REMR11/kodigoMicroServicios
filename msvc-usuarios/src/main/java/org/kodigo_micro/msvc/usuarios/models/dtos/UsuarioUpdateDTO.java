package org.kodigo_micro.msvc.usuarios.models.dtos;

/**
 * Record destinado a la actualizacion de una entidad usuario
 * @param nombre
 * @param email
 * @param password
 * @param state
 */
public record UsuarioUpdateDTO(
        String nombre,
        String email,
        String password,
        boolean state
) {}
