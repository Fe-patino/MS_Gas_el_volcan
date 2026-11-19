package ms.autenticacion.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Configuración principal de seguridad de Spring Security
@Configuration
@EnableWebSecurity
public class ConfiguracionSeguridad {

    @Autowired
    private FiltroJwt filtroJwt;

    @Bean
    public SecurityFilterChain filtroSeguridad(HttpSecurity http) throws Exception {
        http
            // Desactivar CSRF (no necesario con JWT)
            .csrf(csrf -> csrf.disable())

            // Permitir peticiones desde React (CORS)
            .cors(cors -> cors.configure(http))

            // Definir rutas públicas y protegidas
            .authorizeHttpRequests(auth -> auth
                // Rutas públicas — no requieren token
                .requestMatchers(
                    "/api/auth/login",
                    "/api/auth/registro",
                    "/swagger-ui/**",
                    "/v3/api-docs/**"
                ).permitAll()
                // Todo lo demás requiere autenticación
                .anyRequest().authenticated()
            )

            // Sin sesión HTTP — usamos JWT
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // Agregar el filtro JWT antes del filtro de autenticación
            .addFilterBefore(filtroJwt, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Encriptador de contraseñas con BCrypt
    @Bean
    public PasswordEncoder encriptador() {
        return new BCryptPasswordEncoder();
    }
}