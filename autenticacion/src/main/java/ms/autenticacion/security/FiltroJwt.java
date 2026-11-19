package ms.autenticacion.security;

import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;

@Component 
public class FiltroJwt extends OncePerRequestFilter{

    @Autowired 
    private JwtUtil jwtUtil;

    @Override 
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpResponse response,
                                    FilterChain filterChain)


    
    
}
package ms.autenticacion.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

// Filtro que intercepta cada petición y valida el token JWT
@Component
public class FiltroJwt extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Obtener el encabezado Authorization
        String encabezado = request.getHeader("Authorization");

        // Si no hay token o no empieza con "Bearer ", continuar sin autenticar
        if (encabezado == null || !encabezado.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraer el token sin el prefijo "Bearer "
        String token = encabezado.substring(7);

        // Validar el token y autenticar al usuario
        if (jwtUtil.esValido(token)) {
            String correo = jwtUtil.obtenerCorreo(token);
            String rol = jwtUtil.obtenerRol(token);

            // Crear la autenticación con el rol del usuario
            UsernamePasswordAuthenticationToken autenticacion =
                new UsernamePasswordAuthenticationToken(
                    correo,
                    null,
                    List.of(new SimpleGrantedAuthority("ROLE_" + rol))
                );

            // Guardar la autenticación en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(autenticacion);
        }

        filterChain.doFilter(request, response);
    }
}