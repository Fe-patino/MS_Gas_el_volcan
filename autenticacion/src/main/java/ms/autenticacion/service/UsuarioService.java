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

// Servicio con la lógica de autenticación y registro de usuarios
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder encriptador;

    // Registrar nuevo usuario
    public RespuestaAuth registrar(SolicitudRegistro solicitud) {

        // Verificar si el correo ya está registrado
        if (usuarioRepository.existsByCorreo(solicitud.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        // Determinar el rol (por defecto CLIENTE)
        Usuario.Rol rol = Usuario.Rol.CLIENTE;
        if ("ADMINISTRADOR".equalsIgnoreCase(solicitud.getRol())) {
            rol = Usuario.Rol.ADMINISTRADOR;
        }

        // Crear y guardar el usuario con la contraseña encriptada
        Usuario usuario = new Usuario();
        usuario.setNombre(solicitud.getNombre());
        usuario.setCorreo(solicitud.getCorreo());
        usuario.setPassword(encriptador.encode(solicitud.getPassword()));
        usuario.setRol(rol);

        usuarioRepository.save(usuario);

        // Generar token JWT y retornar respuesta
        String token = jwtUtil.generarToken(usuario.getCorreo(), usuario.getRol().name());
        return new RespuestaAuth(token, usuario.getNombre(), usuario.getCorreo(), usuario.getRol().name());
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