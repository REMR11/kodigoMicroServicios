package org.kodigo_micro.msvc.cursos.services;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.kodigo_micro.msvc.cursos.models.Usuario;
import org.kodigo_micro.msvc.cursos.models.dtos.UsuarioDTO;
import org.kodigo_micro.msvc.cursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {
    List<Curso> listar();
    Optional<Curso> porId(@NotNull Long id);
    Curso guardar(@Valid @NotNull Curso curso);
    void eliminar(@NotNull Long id);
    boolean eliminarLogico(@NotNull Long id);


    Optional<Usuario> asignarUsuario(Usuario usuarioDTO, Long cursoId);
    Optional<Usuario> crearUsuario(UsuarioDTO usuarioDTO, Long cursoId);
    Optional<Usuario> removerUsuario(Usuario usuarioDTO, Long cursoId);
}
