package ms.autenticacion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ms.autenticacion.dto.RespuestaAuth;
import ms.autenticacion.dto.SolicitudLogin;
import ms.autenticacion.dto.SolicitudRegistro;
import ms.autenticacion.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Controlador REST para autenticación de usuarios
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "API para login y registro de usuarios Gas El Volcán")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    // POST /api/auth/registro - Registrar nuevo usuario
    @PostMapping("/registro")
    @Operation(summary = "Registrar nuevo usuario")
    public ResponseEntity<RespuestaAuth> registrar(@RequestBody SolicitudRegistro solicitud) {
        return ResponseEntity.ok(usuarioService.registrar(solicitud));
    }

    // POST /api/auth/login - Iniciar sesión
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión y obtener token JWT")
    public ResponseEntity<RespuestaAuth> login(@RequestBody SolicitudLogin solicitud) {
        return ResponseEntity.ok(usuarioService.login(solicitud));
    }
}