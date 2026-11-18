package cl.tienda.contacto.controller;

import cl.tienda.contacto.model.Contacto;
import cl.tienda.contacto.service.ContactoService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/contactos")
@CrossOrigin(origins = "http://localhost:5173")
public class ContactoController {

    private final ContactoService service;

    public ContactoController(ContactoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Contacto> guardar(@RequestBody Contacto contacto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(contacto));
    }

    @GetMapping
    public List<Contacto> listar() {
        return service.listar();
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Contacto> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        return ResponseEntity.ok(service.cambiarEstado(id, estado));
    }
}