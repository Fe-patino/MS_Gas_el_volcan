package ms.autenticacion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "API para login y registro de usuarios Gas El Volcán")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // POST /api/auth/registro - Registro público (siempre crea un CLIENTE)
    @PostMapping("/registro")
    @Operation(summary = "Registrar nuevo cliente")
    public ResponseEntity<RespuestaAuth> registrar(@RequestBody SolicitudRegistro solicitud) {
        return ResponseEntity.ok(usuarioService.registrar(solicitud));
    }

    // POST /api/auth/login - Iniciar sesión
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión y obtener token JWT")
    public ResponseEntity<RespuestaAuth> login(@RequestBody SolicitudLogin solicitud) {
        return ResponseEntity.ok(usuarioService.login(solicitud));
    }

    // GET /api/auth/usuarios - Listar todos los usuarios (solo ADMINISTRADOR)
    @GetMapping("/usuarios")
    @Operation(summary = "Listar todos los usuarios (solo administrador)")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    // POST /api/auth/usuarios - Crear un usuario eligiendo su rol (solo ADMINISTRADOR)
    @PostMapping("/usuarios")
    @Operation(summary = "Crear usuario cliente o administrador (solo administrador)")
    public ResponseEntity<Usuario> crearUsuario(@RequestBody SolicitudRegistro solicitud) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crearUsuario(solicitud));
    }
    // PUT /api/auth/password - Cambiar contraseña del usuario autenticado
    @PutMapping("/password")
    @Operation(summary = "Cambiar contraseña del usuario autenticado")
    public ResponseEntity<Void> cambiarPassword(
            @RequestBody java.util.Map<String, String> body,
            Authentication autenticacion) {
        usuarioService.cambiarPassword(
                autenticacion.getName(),
                body.get("passwordActual"),
                body.get("passwordNueva")
        );
    return ResponseEntity.ok().build();
}

    // DELETE /api/auth/usuarios/{id} - Eliminar usuario por ID (solo ADMINISTRADOR)
    @DeleteMapping("/usuarios/{id}")
    @Operation(summary = "Eliminar usuario por ID (solo administrador)")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id, Authentication autenticacion) {
        usuarioService.eliminar(id, autenticacion.getName());
        return ResponseEntity.noContent().build();
    }
}
