package org.kodigo_micro.msvc.usuarios.repositories;

import org.kodigo_micro.msvc.usuarios.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<List<Usuario>> findByState(Boolean state);
    Optional<Usuario>findByemail(String email);
}
