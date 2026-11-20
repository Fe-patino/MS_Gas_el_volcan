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

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "API para login y registro de usuarios Gas El Volcán")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

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

    // GET /api/auth/usuarios - Listar todos los usuarios
    @GetMapping("/usuarios")
    @Operation(summary = "Listar todos los usuarios")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    // DELETE /api/auth/usuarios/{id} - Eliminar usuario por ID
    @DeleteMapping("/usuarios/{id}")
    @Operation(summary = "Eliminar usuario por ID")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}