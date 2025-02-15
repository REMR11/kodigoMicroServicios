package org.kodigo_micro.msvc.usuarios.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.kodigo_micro.msvc.usuarios.models.dtos.UsuarioDTO;


@Entity
@Table(name="usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @NotEmpty
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Debe ser una dirección de correo electrónico con formato correcto")
    @Column(unique = true)
    private String email;

    @NotBlank
    private String password;

    public Usuario() {
    }

    public Usuario(Long id, String nombre, String email, String password) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    public Usuario(UsuarioDTO usuarioDTO) {
        this.nombre = usuarioDTO.nombre();
        this.email = usuarioDTO.email();
        this.password = usuarioDTO.password();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
