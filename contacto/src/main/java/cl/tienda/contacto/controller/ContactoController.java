package cl.tienda.contacto.controller;

import cl.tienda.contacto.model.Contacto;
import cl.tienda.contacto.service.ContactoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/contactos")
@CrossOrigin(origins = "http://localhost:5173") // Permite peticiones desde el frontend React
@Tag(name = "Contacto", description = "API para formulario de contacto Gas El Volcán")
public class ContactoController {

    private final ContactoService service;

    // Inyección del servicio por constructor
    public ContactoController(ContactoService service) {
        this.service = service;
    }

    // POST /api/contactos - Recibe un mensaje del formulario de contacto
    @PostMapping
    @Operation(summary = "Enviar mensaje de contacto")
    public ResponseEntity<Contacto> guardar(@Valid @RequestBody Contacto contacto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(contacto));
    }

    // GET /api/contactos - Lista todos los mensajes (uso administrativo)
    @GetMapping
    @Operation(summary = "Listar todos los mensajes de contacto")
    public List<Contacto> listar() {
        return service.listar();
    }

    // PUT /api/contactos/{id}/estado - Cambia el estado del mensaje
    @PutMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado de un mensaje (PENDIENTE / RESPONDIDO)")
    public ResponseEntity<Contacto> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        return ResponseEntity.ok(service.cambiarEstado(id, estado));
    }
}
