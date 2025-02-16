package org.kodigo_micro.msvc.usuarios.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.kodigo_micro.msvc.usuarios.models.dtos.UsuarioDTO;
import org.kodigo_micro.msvc.usuarios.models.dtos.UsuarioUpdateDTO;


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
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un carácter especial"
    )
    private String password;

    @NotNull(message = "Un suario siempre debe tener un estado")
    @NotBlank(message = "El stado de usuario no puede estas vacio")
    private boolean state;

    public Usuario() {
    }

    public Usuario(UsuarioUpdateDTO usuarioUpdateDTO) {
        this.nombre = usuarioUpdateDTO.nombre();
        this.email = usuarioUpdateDTO.email();
        this.password = usuarioUpdateDTO.password();
        this.state = usuarioUpdateDTO.state();
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

    public boolean isState() { return state; }

    public void setState(boolean state) {  this.state = state; }
}
