package org.kodigo_micro.msvc.cursos.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Bad Request");
        response.put("message", ex.getMessage());
        response.put("path", ServletUriComponentsBuilder.fromCurrentRequest().toUriString());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(FechaInvalidaException.class)
    public ResponseEntity<Map<String, Object>> handleFechaInvalidaException(FechaInvalidaException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Bad Request");
        response.put("message", ex.getMessage());
        response.put("path", ServletUriComponentsBuilder.fromCurrentRequest().toUriString());

        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(DuracionInvalidaException.class)
    public ResponseEntity<Map<String, Object>> handleDuracionInvalidaException(DuracionInvalidaException ex){
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE.value());
        response.put("error", "Range not satisfiable    ");
        response.put("message", ex.getMessage());
        response.put("path", ServletUriComponentsBuilder.fromCurrentRequest().toUriString());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NotaNegativaException.class)
    public  ResponseEntity<Map<String, Object>> handleNotaMinimaException(NotaNegativaException ex){
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.NOT_ACCEPTABLE.value());
        response.put("error", "Not acceptable");
        response.put("message", ex.getMessage());
        response.put("path", ServletUriComponentsBuilder.fromCurrentRequest().toUriString());

        return ResponseEntity.badRequest().body(response);
    }
}

