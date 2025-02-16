package org.kodigo_micro.msvc.cursos.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.kodigo_micro.msvc.cursos.models.dtos.CursoDTO;
import org.kodigo_micro.msvc.cursos.models.dtos.CursoUpdateDTO;

import java.time.LocalDate;


@Entity
@Table(name = "cursos")
public class Curso {
    /***
     * Constructor vacio
     */
    public Curso() {}


    /***
     * Contructor que es utilizado para convertir un tipo CursoUpdateDTO para modificar un curso existente
     * @param cursoUpdateDTO
     */
    public Curso(CursoUpdateDTO cursoUpdateDTO) {
        this.nombre = cursoUpdateDTO.nombre();
        this.inicio = cursoUpdateDTO.inicio();
        this.finalizacion = cursoUpdateDTO.finalizacion();
        this.notaMinima = cursoUpdateDTO.notaMinima();
        this.state = cursoUpdateDTO.state();
    }

    /***
     * Contructor que consume CursoDTO destinado a crear un nuevo curso
     * @param cursoDTO
     */
    public Curso(CursoDTO cursoDTO) {
        this.nombre = cursoDTO.nombre();
        this.inicio = cursoDTO.inicio();
        this.finalizacion = cursoDTO.finalizacion();
        this.notaMinima = cursoDTO.notaMinima();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre del curso no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @NotNull(message = "La fecha de inicio no puede ser nula")
    private LocalDate inicio;

    @NotNull(message = "La fecha de finalización no puede ser nula")
    private LocalDate finalizacion;

    @NotNull(message = "La nota mínima del curso no puede ser nula")
    private Double notaMinima = 8.0;

    @NotNull()
    private boolean state = true;
    @AssertTrue(message = "La fecha de inicio debe ser antes de la finalización")
    public boolean isHorarioValido() {
        if (inicio == null || finalizacion == null) {
            return false; // Ambas fechas deben estar definidas
        }
        return inicio.isBefore(finalizacion);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getInicio() {
        return this.inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFinalizacion() {
        return this.finalizacion;
    }

    public void setFinalizacion(LocalDate finalizacion) {
        this.finalizacion = finalizacion;
    }

    public Double getNotaMinima() {
        return notaMinima;
    }

    public void setNotaMinima(Double notaMinima) {
        this.notaMinima = notaMinima;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
