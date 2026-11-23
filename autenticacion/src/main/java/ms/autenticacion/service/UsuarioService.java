package ms.autenticacion.service;

import ms.autenticacion.dto.RespuestaAuth;
import ms.autenticacion.dto.SolicitudLogin;
import ms.autenticacion.dto.SolicitudRegistro;
import ms.autenticacion.model.Usuario;
import ms.autenticacion.repository.UsuarioRepository;
import ms.autenticacion.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

// Servicio con la lógica de autenticación y registro de usuarios
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder encriptador;

    // Registro público: SIEMPRE crea un CLIENTE (se ignora el rol que venga en la solicitud).
    // Los administradores solo los puede crear otro administrador (ver crearUsuario).
    public RespuestaAuth registrar(SolicitudRegistro solicitud) {
        Usuario usuario = guardar(solicitud, Usuario.Rol.CLIENTE);

        // Generar token JWT y retornar respuesta
        String token = jwtUtil.generarToken(usuario.getCorreo(), usuario.getRol().name());
        return new RespuestaAuth(token, usuario.getNombre(), usuario.getCorreo(), usuario.getRol().name());
    }

    // Crear usuario desde el panel de administración: el administrador elige el rol
    public Usuario crearUsuario(SolicitudRegistro solicitud) {
        Usuario.Rol rol = Usuario.Rol.CLIENTE;
        String rolSolicitado = solicitud.getRol();
        if (rolSolicitado != null && !rolSolicitado.isBlank()) {
            try {
                rol = Usuario.Rol.valueOf(rolSolicitado.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rol no válido: use CLIENTE o ADMINISTRADOR");
            }
        }
        return guardar(solicitud, rol);
    }
    public void cambiarPassword(String correo, String passwordActual, String passwordNueva) {
    Usuario usuario = usuarioRepository.findByCorreo(correo)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    if (!encriptador.matches(passwordActual, usuario.getPassword())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La contraseña actual es incorrecta");
    }
    if (passwordNueva == null || passwordNueva.length() < 4 || passwordNueva.length() > 20) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La nueva contraseña debe tener entre 4 y 20 caracteres");
    }
    usuario.setPassword(encriptador.encode(passwordNueva));
    usuarioRepository.save(usuario);
}

    // Eliminar un usuario (404 si no existe). Un administrador no puede eliminarse a sí mismo.
    public void eliminar(Long id, String correoQuienElimina) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario " + id + " no encontrado"));
        if (usuario.getCorreo().equalsIgnoreCase(correoQuienElimina)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No puedes eliminar tu propia cuenta");
        }
        usuarioRepository.delete(usuario);
    }

    // Valida los datos, verifica que el correo no exista y guarda el usuario con la contraseña encriptada
    private Usuario guardar(SolicitudRegistro solicitud, Usuario.Rol rol) {
        if (solicitud.getNombre() == null || solicitud.getNombre().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre es obligatorio");
        }
        if (solicitud.getCorreo() == null || !solicitud.getCorreo().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El correo no es válido");
        }
        if (solicitud.getPassword() == null || solicitud.getPassword().length() < 4 || solicitud.getPassword().length() > 20) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La contraseña debe tener entre 4 y 20 caracteres");
        }
        if (usuarioRepository.existsByCorreo(solicitud.getCorreo())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(solicitud.getNombre().trim());
        usuario.setCorreo(solicitud.getCorreo().trim());
        usuario.setPassword(encriptador.encode(solicitud.getPassword()));
        usuario.setRol(rol);
        return usuarioRepository.save(usuario);
    }

    // Iniciar sesión
    public RespuestaAuth login(SolicitudLogin solicitud) {

        // Buscar usuario por correo
        Usuario usuario = usuarioRepository.findByCorreo(solicitud.getCorreo())
                .orElseThrow(() -> new RuntimeException("Correo o contraseña incorrectos"));

        // Verificar contraseña
        if (!encriptador.matches(solicitud.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Correo o contraseña incorrectos");
        }

        // Generar token JWT y retornar respuesta
        String token = jwtUtil.generarToken(usuario.getCorreo(), usuario.getRol().name());
        return new RespuestaAuth(token, usuario.getNombre(), usuario.getCorreo(), usuario.getRol().name());
    }
}
