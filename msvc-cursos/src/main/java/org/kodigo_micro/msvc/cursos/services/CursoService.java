package org.kodigo_micro.msvc.cursos.services;

import jakarta.validation.constraints.NotNull;
import org.kodigo_micro.msvc.cursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {
    List<Curso> listar();
    Optional<Curso> porId(@NotNull Long id);
    Curso guardar(@NotNull Curso curso);
    void eliminar(@NotNull Long id);
    boolean eliminarLogico(@NotNull Long id);
}
