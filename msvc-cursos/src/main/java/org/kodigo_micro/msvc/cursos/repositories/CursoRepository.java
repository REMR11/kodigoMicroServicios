package org.kodigo_micro.msvc.cursos.repositories;

import org.kodigo_micro.msvc.cursos.models.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
