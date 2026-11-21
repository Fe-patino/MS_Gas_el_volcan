package ms.productos.service;

import ms.productos.model.Producto;
import ms.productos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

// Servicio que contiene la lógica de negocio para Producto
@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // Obtener todos los productos
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    // Obtener un producto por ID
    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id);
    }

    // Crear un nuevo producto
    public Producto crearProducto(Producto producto) {
        producto.setId(null); // el id lo asigna la base de datos
        validar(producto);
        return productoRepository.save(producto);
    }

    // Actualizar un producto existente
    public Producto actualizarProducto(Long id, Producto productoActualizado) {
        if (!productoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto " + id + " no encontrado");
        }
        productoActualizado.setId(id);
        validar(productoActualizado);
        return productoRepository.save(productoActualizado);
    }

    // Eliminar un producto por ID
    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto " + id + " no encontrado");
        }
        productoRepository.deleteById(id);
    }

    // Reglas básicas: datos obligatorios y textos nunca nulos (el frontend los usa para buscar)
    private void validar(Producto p) {
        if (p.getNombre() == null || p.getNombre().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre es obligatorio");
        }
        if (p.getPrecio() == null || p.getPrecio() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El precio debe ser mayor a 0");
        }
        if (p.getStock() == null || p.getStock() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El stock no puede ser negativo");
        }
        p.setNombre(p.getNombre().trim());
        if (p.getPrecioComercial() == null) p.setPrecioComercial(0.0);
        if (p.getDescripcionBreve() == null) p.setDescripcionBreve("");
        if (p.getDescripcionCompleta() == null) p.setDescripcionCompleta("");
    }
}
