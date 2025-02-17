package org.kodigo_micro.msvc.usuarios.services;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.kodigo_micro.msvc.usuarios.exceptions.UsuarioNotFoundException;
import org.kodigo_micro.msvc.usuarios.models.entity.Usuario;
import org.kodigo_micro.msvc.usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<Usuario>> ListUserByState(boolean state) {
        return repository.findByState(state);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Usuario> porId(@NotNull Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return repository.findByemail(email);
    }

    @Override
    @Transactional
    public Usuario guardar(@Valid @NotNull Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    @Transactional
    public void eliminar(@NotNull Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public boolean desactivarUsuario(Long id) {
        return  repository.findById(id)
                .map(usuario ->{
                    usuario.setState(false);
                    repository.save(usuario);
                    return true;
                }).orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado",id));
    }
}
