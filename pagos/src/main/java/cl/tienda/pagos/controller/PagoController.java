package cl.tienda.pagos.controller;

import cl.tienda.pagos.model.Pago;
import cl.tienda.pagos.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static cl.tienda.pagos.security.ConfiguracionSeguridad.ROL_ADMIN;

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
    @Operation(summary = "Listar pagos de un cliente por correo (el propio cliente o un administrador)")
    public List<Pago> listarPorCorreo(@PathVariable String correo, Authentication auth) {
        boolean esAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + ROL_ADMIN));
        if (!esAdmin && !auth.getName().equalsIgnoreCase(correo)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Solo puedes ver tus propios pagos");
        }
        return service.listarPorCorreo(correo);
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo pago (queda a nombre del usuario autenticado)")
    public ResponseEntity<Pago> registrar(@RequestBody Pago pago, Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(pago, auth.getName()));
    }
}
