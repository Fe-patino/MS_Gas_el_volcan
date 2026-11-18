package cl.tienda.contacto.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "contacto")
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_completo", nullable = false, length = 150)
    private String nombreCompleto;

    @Column(nullable = false, length = 150)
    private String correo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    @Column(nullable = false, length = 20)
    private String estado = "PENDIENTE";

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void asignarFecha() {
        this.fechaCreacion = LocalDateTime.now();
    }
}
