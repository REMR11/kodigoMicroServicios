package org.kodigo_micro.msvc.usuarios.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.kodigo_micro.msvc.usuarios.exceptions.UsuarioNotFoundException;
import org.kodigo_micro.msvc.usuarios.models.dtos.UsuarioDTO;
import org.kodigo_micro.msvc.usuarios.models.dtos.UsuarioUpdateDTO;
import org.kodigo_micro.msvc.usuarios.models.entity.Usuario;
import org.kodigo_micro.msvc.usuarios.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "API para la gestión de usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos los usuarios",
            description = "Obtiene una lista de todos los usuarios registrados")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente")
    @ApiResponse(responseCode = "204", description = "No se encontraron usuarios")
    public ResponseEntity<List<Usuario>> listar() {
        List<Usuario> usuarios = service.listar();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/estado")
    @Operation(summary = "Listar usuarios por estado",
            description = "Obtiene una lista de usuarios filtrada por su estado")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios filtrada exitosamente")
    @ApiResponse(responseCode = "204", description = "No se encontraron usuarios con el estado especificado")
    public ResponseEntity<List<Usuario>> listarPorEstado(
            @Parameter(description = "Estado del usuario (true=activo, false=inactivo)")
            @RequestParam(required = false) Boolean estado) {

        List<Usuario> usuarios = estado != null
                ? service.ListUserByState(estado).orElse(List.of())
                : service.listar();

        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID",
            description = "Obtiene un usuario específico por su ID")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    public ResponseEntity<Usuario> detalle(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable Long id) {
        return service.porId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado ", id));
    }

    @PostMapping
    @Operation(summary = "Crear nuevo usuario",
            description = "Crea un nuevo usuario con la información proporcionada")
    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos del usuario inválidos")
    public ResponseEntity<?> crear(
            @Valid @RequestBody UsuarioDTO usuario,
            BindingResult result) {

        if (result.hasErrors()) {
            return validar(result);
        }

        Usuario usuariodb = new Usuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.guardar(usuariodb));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario",
            description = "Actualiza un usuario existente por su ID")
    @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos del usuario inválidos")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    public ResponseEntity<?> editar(
            @Valid @RequestBody UsuarioUpdateDTO usuario,
            BindingResult result,
            @Parameter(description = "ID del usuario a actualizar", required = true)
            @PathVariable Long id) {

        if (result.hasErrors()) {
            return validar(result);
        }

        return service.porId(id)
                .map(usuarioDb -> {
                    actualizarUsuario(usuarioDb, usuario);
                    return ResponseEntity.ok(service.guardar(usuarioDb));
                })
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado ", id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario",
            description = "Elimina un usuario por su ID")
    @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    public ResponseEntity<?> eliminar(
            @Parameter(description = "ID del usuario a eliminar", required = true)
            @PathVariable Long id) {

        if (service.porId(id).isPresent()) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        throw new UsuarioNotFoundException("Usuario no encontrado ", id);
    }

    @PatchMapping("/{id}/desactivar")
    @Operation(summary = "Desactivar usuario",
            description = "Realiza un borrado lógico del usuario, cambiando su estado a inactivo")
    @ApiResponse(responseCode = "200", description = "Usuario desactivado exitosamente")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    public ResponseEntity<Map<String, String>> desactivar(
            @Parameter(description = "ID del usuario a desactivar", required = true)
            @PathVariable Long id) {

        if (service.desactivarUsuario(id)) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Usuario desactivado exitosamente");
            return ResponseEntity.ok(response);
        }
        throw new UsuarioNotFoundException("Usuario no encontrado con ID: ", id);
    }

    private void actualizarUsuario(Usuario usuarioDb, UsuarioUpdateDTO usuario) {
        usuarioDb.setNombre(usuario.nombre());
        usuarioDb.setEmail(usuario.email());
        if (usuario.password() != null && !usuario.password().isEmpty()) {
            usuarioDb.setPassword(usuario.password());
        }
        usuarioDb.setState(usuario.state());
    }

    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err ->
                errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errores);
    }
}