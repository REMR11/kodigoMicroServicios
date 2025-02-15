package org.kodigo_micro.msvc.usuarios.controllers;

import jakarta.validation.Valid;
import org.kodigo_micro.msvc.usuarios.models.dtos.UsuarioDTO;
import org.kodigo_micro.msvc.usuarios.models.entity.Usuario;
import org.kodigo_micro.msvc.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<Usuario> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = service.porId(id);
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.ok(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody UsuarioDTO usuario, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }
        Usuario usuariodb = new Usuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuariodb));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody UsuarioDTO usuario, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {  return validar(result); }

        Optional<Usuario> o = service.porId(id);
        if (o.isPresent()) {
            Usuario usuarioDb = o.get();
            usuarioDb.setNombre(usuario.nombre());
            usuarioDb.setEmail(usuario.email());
            usuarioDb.setPassword(usuario.password());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuarioDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Usuario> o = service.porId(id);
        if (o.isPresent()) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
