package cl.tienda.novedades.controller;

import cl.tienda.novedades.model.Novedad;
import cl.tienda.novedades.service.NovedadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/novedades")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Novedades", description = "API para novedades Gas El Volcán")
public class NovedadController {

    private final NovedadService service;

    public NovedadController(NovedadService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todas las novedades")
    public List<Novedad> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener novedad por ID")
    public ResponseEntity<Novedad> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear nueva novedad")
    public ResponseEntity<Novedad> crear(@RequestBody Novedad novedad) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(novedad));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar novedad existente")
    public ResponseEntity<Novedad> actualizar(@PathVariable Long id, @RequestBody Novedad novedad) {
        return ResponseEntity.ok(service.actualizar(id, novedad));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar novedad por ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}