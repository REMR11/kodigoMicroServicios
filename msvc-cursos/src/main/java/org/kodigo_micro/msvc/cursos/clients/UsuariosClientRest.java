package org.kodigo_micro.msvc.cursos.clients;

import org.kodigo_micro.msvc.cursos.models.Usuario;
import org.kodigo_micro.msvc.cursos.models.dtos.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "msvc-usuarios", url = "http://localhost:8001")
public interface UsuariosClientRest {

    @GetMapping("/{id}")
    ResponseEntity<Usuario> detalle(@PathVariable Long usuarioid);

    @PostMapping
    ResponseEntity<Usuario> crear(@RequestBody UsuarioDTO usuario);


}
