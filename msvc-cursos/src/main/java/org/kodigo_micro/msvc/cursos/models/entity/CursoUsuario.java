package org.kodigo_micro.msvc.cursos.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cursos_usuario")
public class CursoUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", unique = true)
    private Long usuarioId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof CursoUsuario)) return false;

        CursoUsuario a = (CursoUsuario) obj;
        return this.usuarioId!= null && this.usuarioId.equals(a.usuarioId);
    }
}
