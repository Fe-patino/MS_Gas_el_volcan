package cl.tienda.pagos.controller;

import cl.tienda.pagos.model.Pago;
import cl.tienda.pagos.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Pagos", description = "API para registro de pagos Gas El Volcán")
public class PagoController {

    private final PagoService service;

    public PagoController(PagoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos los pagos (admin)")
    public List<Pago> listar() {
        return service.listar();
    }

    @GetMapping("/cliente/{correo}")
    @Operation(summary = "Listar pagos de un cliente por correo")
    public List<Pago> listarPorCorreo(@PathVariable String correo) {
        return service.listarPorCorreo(correo);
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo pago")
    public ResponseEntity<Pago> registrar(@RequestBody Pago pago) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(pago));
    }
}