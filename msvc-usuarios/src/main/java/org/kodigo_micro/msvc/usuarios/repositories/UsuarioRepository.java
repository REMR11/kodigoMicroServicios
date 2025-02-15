package org.kodigo_micro.msvc.usuarios.repositories;

import org.kodigo_micro.msvc.usuarios.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
}
