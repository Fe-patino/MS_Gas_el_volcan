package cl.tienda.pagos.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_cliente", nullable = false, length = 150)
    private String nombreCliente;

    @Column(name = "correo_cliente", nullable = false, length = 150)
    private String correoCliente;

    @Column(nullable = false)
    private Double total;

    @Column(name = "ultimos_digitos", nullable = false, length = 4)
    private String ultimosDigitos;

    @Column(nullable = false, length = 20)
    private String estado = "COMPLETADO";

    @Column(name = "fecha_pago", nullable = false, updatable = false)
    private LocalDateTime fechaPago;

    @PrePersist
    protected void asignarFecha() {
        this.fechaPago = LocalDateTime.now();
    }
}