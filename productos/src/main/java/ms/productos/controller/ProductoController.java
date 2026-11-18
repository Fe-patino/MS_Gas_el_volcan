package ms.productos.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ms.productos.model.Producto;
import ms.productos.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador REST para gestión de productos
@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "API para gestión de productos Gas El Volcán")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // GET /api/productos - Listar todos los productos
    @GetMapping
    @Operation(summary = "Listar todos los productos")
    public List<Producto> listar() {
        return productoService.listarProductos();
    }

    // GET /api/productos/{id} - Obtener producto por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        return productoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/productos - Crear nuevo producto
    @PostMapping
    @Operation(summary = "Crear nuevo producto")
    public Producto crear(@RequestBody Producto producto) {
        return productoService.crearProducto(producto);
    }

    // PUT /api/productos/{id} - Actualizar producto
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto existente")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.actualizarProducto(id, producto));
    }

    // DELETE /api/productos/{id} - Eliminar producto
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto por ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}