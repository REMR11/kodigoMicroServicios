package org.kodigo_micro.msvc.usuarios.exceptions;

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
    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUsuarioNotFoundException(ConstraintViolationException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", "Not Found");
        response.put("message", ex.getMessage());
        response.put("path", ServletUriComponentsBuilder.fromCurrentRequest().toUriString());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(EmailDuplicateException.class)
    public ResponseEntity<Map<String, Object>> handleEmailDuplicateException(EmailDuplicateException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.SEE_OTHER.value());
        response.put("error", "See other");
        response.put("message", ex.getMessage());
        response.put("path", ServletUriComponentsBuilder.fromCurrentRequest().toUriString());

        return ResponseEntity.badRequest().body(response);
    }
}
