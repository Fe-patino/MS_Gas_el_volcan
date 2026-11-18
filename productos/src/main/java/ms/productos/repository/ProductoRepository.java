package ms.productos.repository;

import ms.productos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repositorio JPA para operaciones CRUD de Producto
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // JpaRepository provee automáticamente:
    // findAll()       - listar todos los productos
    // findById(id)    - buscar producto por ID
    // save(producto)  - crear o actualizar producto
    // deleteById(id)  - eliminar producto por ID
}