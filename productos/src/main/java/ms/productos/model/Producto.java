package ms.productos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column
    private String categoria;

    @Column(nullable = false)
    private Double precio;

    @Column
    private Double precioComercial;

    @Column(nullable = false)
    private Integer stock;

    @Column(length = 500)
    private String descripcion;

    @Column(length = 500)
    private String descripcionBreve;

    @Column(length = 1000)
    private String descripcionCompleta;

    @Column
    private String imagen;
}
