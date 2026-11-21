package ms.autenticacion.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;
import java.util.Date;

// Utilidad para generar y validar tokens JWT
@Component
public class JwtUtil {

    // Clave secreta para firmar el token (mínimo 256 bits). Viene de la propiedad jwt.secreto
    @Value("${jwt.secreto}")
    private String secreto;

    // Tiempo de expiración: 24 horas en milisegundos
    private static final long EXPIRACION = 86400000;

    private SecretKey obtenerClave() {
        return Keys.hmacShaKeyFor(secreto.getBytes(StandardCharsets.UTF_8));
    }

    // Generar token JWT con correo y rol del usuario
    public String generarToken(String correo, String rol) {
        return Jwts.builder()
                .subject(correo)
                .claim("rol", rol)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRACION))
                .signWith(obtenerClave())
                .compact();
    }

    // Obtener el correo desde el token
    public String obtenerCorreo(String token) {
        return obtenerClaims(token).getSubject();
    }

    // Obtener el rol desde el token
    public String obtenerRol(String token) {
        return obtenerClaims(token).get("rol", String.class);
    }

    // Verificar si el token es válido
    public boolean esValido(String token) {
        try {
            obtenerClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Extraer todos los claims del token
    private Claims obtenerClaims(String token) {
        return Jwts.parser()
                .verifyWith(obtenerClave())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
