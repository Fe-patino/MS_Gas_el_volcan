package ms.autenticacion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import ms.autenticacion.dto.RespuestaAuth;
import ms.autenticacion.dto.SolicitudLogin;
import ms.autenticacion.dto.SolicitudRegistro;
import ms.autenticacion.model.Usuario;
import ms.autenticacion.repository.UsuarioRepository;
import ms.autenticacion.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "API para login y registro de usuarios Gas El Volcán")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/registro")
    @Operation(summary = "Registrar nuevo cliente")
    public ResponseEntity<RespuestaAuth> registrar(@org.springframework.web.bind.annotation.RequestBody SolicitudRegistro solicitud) {
        return ResponseEntity.ok(usuarioService.registrar(solicitud));
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión y obtener token JWT")
    public ResponseEntity<RespuestaAuth> login(@org.springframework.web.bind.annotation.RequestBody SolicitudLogin solicitud) {
        return ResponseEntity.ok(usuarioService.login(solicitud));
    }

    @GetMapping("/usuarios")
    @Operation(summary = "Listar todos los usuarios (solo administrador)")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    @PostMapping("/usuarios")
    @Operation(summary = "Crear usuario cliente o administrador (solo administrador)")
    public ResponseEntity<Usuario> crearUsuario(@org.springframework.web.bind.annotation.RequestBody SolicitudRegistro solicitud) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crearUsuario(solicitud));
    }

    @PutMapping("/password")
    @Operation(summary = "Cambiar contraseña del usuario autenticado")
    @RequestBody(
        content = @Content(
            examples = @ExampleObject(
                value = "{\"passwordActual\": \"123456\", \"passwordNueva\": \"nuevaClave123\"}"
            )
        )
    )
    public ResponseEntity<Void> cambiarPassword(
            @org.springframework.web.bind.annotation.RequestBody Map<String, String> body,
            Authentication autenticacion) {
        usuarioService.cambiarPassword(
                autenticacion.getName(),
                body.get("passwordActual"),
                body.get("passwordNueva")
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/usuarios/{id}")
    @Operation(summary = "Eliminar usuario por ID (solo administrador)")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id, Authentication autenticacion) {
        usuarioService.eliminar(id, autenticacion.getName());
        return ResponseEntity.noContent().build();
    }
}