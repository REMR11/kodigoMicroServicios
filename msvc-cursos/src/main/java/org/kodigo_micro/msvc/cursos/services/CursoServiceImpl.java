package org.kodigo_micro.msvc.cursos.services;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.kodigo_micro.msvc.cursos.exceptions.CursoNotFoundException;
import org.kodigo_micro.msvc.cursos.exceptions.DuracionInvalidaException;
import org.kodigo_micro.msvc.cursos.exceptions.FechaInvalidaException;
import org.kodigo_micro.msvc.cursos.exceptions.NotaNegativaException;
import org.kodigo_micro.msvc.cursos.models.entity.Curso;
import org.kodigo_micro.msvc.cursos.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class CursoServiceImpl implements CursoService{

    @Autowired
    private CursoRepository repository;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "cursos")
    public List<Curso> listar() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "curso", key = "#id")
    public Optional<Curso> porId(@NotNull Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"cursos", "curso"}, allEntries = true)
    public Curso guardar(@Valid @NotNull Curso curso) {
        validarDatosCurso(curso);
        return repository.save(curso);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"cursos", "curso"}, allEntries = true)
    public void eliminar(@NotNull Long id) {
        if (!repository.existsById(id)) {
            throw new CursoNotFoundException("Curso no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public boolean eliminarLogico(@NotNull Long id) {
        return repository.findById(id)
                .map(curso -> {
                    curso.setState(false);
                    repository.save(curso);
                    return true;
                })
                .orElseThrow(() -> new CursoNotFoundException("Curso no encontrado con id: " + id));
    }

    /**
     * Metodo que contiene las validaciones necesarias para guardar un curso en la base de datos.
     * @param curso
     */
    private void validarDatosCurso(Curso curso){
        validarNotaMinima(curso); // Valida que la nota minina de un curso nunca sea negativa
        validarFechasCurso(curso); // Valida que la fecha final no sea antes que la fecha inicial
        validarDuracionMinima(curso); // Validamos la duración mínima antes de guardar
    }

    /**
     * Valida si la nota minima en el curso no sea negativa,
     * si lo es retorna una excepcion.
     * @param curso
     * @throws  NotaNegativaException
     */
    private void validarNotaMinima(Curso curso){
        if(curso.getNotaMinima()<= 0)
            throw new NotaNegativaException(curso.getNotaMinima(), curso.getNombre());
    }
    /**
     * Valida si la duracion y formato de fecha cumple con estandares establecidos,
     * si no cumple, retorna una excepcion.
     * @param curso
     * @throws FechaInvalidaException
     */
    private void validarFechasCurso(Curso curso) {
        if (curso.getInicio().isAfter(curso.getFinalizacion()))
            throw new FechaInvalidaException(curso.getInicio(), curso.getFinalizacion(), "Curso");

    }

    /**
     * Valida que la duración del curso sea de al menos 7 días.
     * Si no cumple, retorna una excepcion.
     * @param curso Curso a validar
     * @throws DuracionInvalidaException si la duración es menor a 7 días
     */
    private void validarDuracionMinima(Curso curso) {
        if (curso.getInicio() != null && curso.getFinalizacion() != null) {
            long diasDuracion = ChronoUnit.DAYS.between(curso.getInicio(), curso.getFinalizacion());

            if (diasDuracion < 7) {
                throw new DuracionInvalidaException(curso.getInicio(), curso.getFinalizacion(), diasDuracion, "Curso");
            }
        }
    }
}
