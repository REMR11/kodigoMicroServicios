package org.kodigo_micro.msvc.cursos.controllers;

import jakarta.validation.Valid;
import org.kodigo_micro.msvc.cursos.models.dtos.CursoDTO;
import org.kodigo_micro.msvc.cursos.models.entity.Curso;
import org.kodigo_micro.msvc.cursos.services.CursoService;
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
public class CursoController {

    @Autowired
    private CursoService service;

    @GetMapping
    public ResponseEntity<List<Curso>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Curso> o = service.porId(id);
        if (o.isPresent()) {
            return ResponseEntity.ok(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> crear(@Valid @RequestBody CursoDTO cursoDTO, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }
        Curso cursoDb = new Curso(cursoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(cursoDb));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody CursoDTO curso, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validar(result);
        }
        Optional<Curso> o = service.porId(id);
        if (o.isPresent()) {
            Curso cursoDb = o.get();
            cursoDb.setNombre(curso.nombre());
            cursoDb.setInicio(curso.inicio());
            cursoDb.setFinalizacion(curso.finalizacion());
            cursoDb.setNotaMinima(curso.notaMinima());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(cursoDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Curso> o = service.porId(id);
        if (o.isPresent()) {
            service.eliminar(o.get().getId());
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
