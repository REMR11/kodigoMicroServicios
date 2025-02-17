package org.kodigo_micro.msvc.usuarios.services;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.kodigo_micro.msvc.usuarios.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> listar();
    Optional<List<Usuario>> ListUserByState(@NotNull boolean state);
    Optional<Usuario> porId(@NotNull Long id);
    Optional<Usuario> buscarPorEmail(@NotEmpty String email);
    Usuario guardar(@Valid @NotNull Usuario usuario);
    void eliminar(@NotNull Long id);
    boolean desactivarUsuario(@NotNull Long id);
}
