package org.kodigo_micro.msvc.cursos.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.kodigo_micro.msvc.cursos.exceptions.CursoNotFoundException;
import org.kodigo_micro.msvc.cursos.exceptions.FechaInvalidaException;
import org.kodigo_micro.msvc.cursos.models.dtos.CursoDTO;
import org.kodigo_micro.msvc.cursos.models.dtos.CursoFechaDTO;
import org.kodigo_micro.msvc.cursos.models.dtos.CursoUpdateDTO;
import org.kodigo_micro.msvc.cursos.models.entity.Curso;
import org.kodigo_micro.msvc.cursos.services.CursoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cursos")
@Tag(name = "Cursos", description = "API para la gestión de cursos")
public class CursoController {

    private final CursoService service;

    public CursoController(CursoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos los cursos", description = "Obtiene una lista de todos los cursos activos")
    @ApiResponse(responseCode = "200", description = "Lista de cursos obtenida exitosamente")
    public ResponseEntity<List<Curso>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener curso por ID", description = "Obtiene un curso específico por su ID")
    @ApiResponse(responseCode = "200", description = "Curso encontrado")
    @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    public ResponseEntity<?> detalle(
            @Parameter(description = "ID del curso", required = true)
            @PathVariable Long id) {
        return service.porId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear nuevo curso", description = "Crea un nuevo curso con la información proporcionada")
    @ApiResponse(responseCode = "201", description = "Curso creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos del curso inválidos")
    public ResponseEntity<?> crear(@Valid @RequestBody CursoDTO cursoDTO, BindingResult result) throws FechaInvalidaException {
        if (result.hasErrors()) { return validar(result); }
        validarFechas(cursoDTO);
        Curso cursoDb = new Curso(cursoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(cursoDb));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar curso", description = "Actualiza un curso existente por su ID")
    @ApiResponse(responseCode = "200", description = "Curso actualizado exitosamente")
    @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    public ResponseEntity<?> editar(
            @Valid @RequestBody CursoUpdateDTO cursoUpdateDTO,
            BindingResult result,
            @Parameter(description = "ID del curso a actualizar", required = true)
            @PathVariable Long id) throws FechaInvalidaException {

        if (result.hasErrors()) {
            return validar(result);
        }

        validarFechas(cursoUpdateDTO);

        return service.porId(id)
                .map(cursoDb -> {
                    actualizarCurso(cursoDb, cursoUpdateDTO);
                    return ResponseEntity.ok(service.guardar(cursoDb));
                })
                .orElseThrow(() -> new CursoNotFoundException("Curso no encontrado con ID: " + id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar curso", description = "Elimina un curso por su ID")
    @ApiResponse(responseCode = "204", description = "Curso eliminado exitosamente")
    @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del curso a eliminar", required = true)
            @PathVariable Long id) {

        if (service.porId(id).isPresent()) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        throw new CursoNotFoundException("Curso no encontrado con ID: " + id);
    }

    @PatchMapping("/{id}/desactivar")
    @Operation(summary = "Desactivar curso",
            description = "Realiza un borrado lógico del curso, cambiando su estado a inactivo")
    @ApiResponse(responseCode = "200", description = "Curso desactivado exitosamente")
    @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    public ResponseEntity<Map<String, String>> desactivar(
            @Parameter(description = "ID del curso a desactivar", required = true)
            @PathVariable Long id) {

        boolean desactivado = service.eliminarLogico(id);
        if (desactivado) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Curso desactivado exitosamente");
            return ResponseEntity.ok(response);
        }
        throw new CursoNotFoundException("Curso no encontrado con ID: " + id);
    }

    /**
     * Metodo generico que valida si las fechas son validas
     * @param cursoDTO
     * @param <T>
     */
    private <T extends CursoFechaDTO> void validarFechas(T cursoDTO) {
        if (cursoDTO.inicio() != null && cursoDTO.finalizacion() != null &&
                cursoDTO.inicio().isAfter(cursoDTO.finalizacion())) {
            throw new FechaInvalidaException(cursoDTO.inicio(), cursoDTO.finalizacion(), cursoDTO.getClass().getSimpleName());
        }
    }

    /**
     * Metodo generico que setea datos de fecha  al momento de actualizar un curso
     * @param cursoDb
     * @param cursoDTO
     * @param <T>
     */
    private <T extends CursoFechaDTO> void actualizarCurso(Curso cursoDb, T cursoDTO) {
        cursoDb.setInicio(cursoDTO.inicio());
        cursoDb.setFinalizacion(cursoDTO.finalizacion());
    }


    /**
     * Convierte los errores del BindingResult para ser mostrados al desarrollador de manera mas amigable
     * @param result
     * @return
     */
    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err ->
                errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errores);
    }

}