package cl.tienda.pagos.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.List;

// Seguridad del microservicio: valida el JWT que emite ms-autenticacion y aplica los roles
@Configuration
@EnableWebSecurity
public class ConfiguracionSeguridad {

    // Rol del administrador tal como lo emite ms-autenticacion (claim "rol")
    public static final String ROL_ADMIN = "ADMINISTRADOR";

    @Value("${jwt.secreto}")
    private String secreto;

    @Bean
    public SecurityFilterChain filtroSeguridad(HttpSecurity http) throws Exception {
        http
            // CSRF no aplica: la autenticación es por token, sin cookies de sesión
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Preflight de CORS y documentación Swagger
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                // Listado general de pagos: solo administrador
                .requestMatchers(HttpMethod.GET, "/api/pagos").hasRole(ROL_ADMIN)
                // Registrar un pago y ver los propios: cualquier usuario con sesión (el controlador verifica que sea suyo)
                .requestMatchers("/api/pagos/**").authenticated()
                // Cualquier otra ruta requiere haber iniciado sesión
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth -> oauth.jwt(jwt -> jwt
                .decoder(decodificadorJwt())
                .jwtAuthenticationConverter(convertidorRoles())));
        return http.build();
    }

    // Valida la firma (HS256) y la expiración con el mismo secreto de ms-autenticacion
    @Bean
    public JwtDecoder decodificadorJwt() {
        SecretKeySpec clave = new SecretKeySpec(secreto.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(clave).macAlgorithm(MacAlgorithm.HS256).build();
    }

    // El claim "rol" (CLIENTE / ADMINISTRADOR) pasa a ser la autoridad ROLE_<rol>
    private JwtAuthenticationConverter convertidorRoles() {
        JwtGrantedAuthoritiesConverter autoridades = new JwtGrantedAuthoritiesConverter();
        autoridades.setAuthoritiesClaimName("rol");
        autoridades.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter convertidor = new JwtAuthenticationConverter();
        convertidor.setJwtGrantedAuthoritiesConverter(autoridades);
        return convertidor;
    }

    // CORS: solo el frontend React puede llamar a este servicio desde el navegador
    @Bean
    public CorsConfigurationSource corsConfigurationSource(
            @Value("${cors.origenes:http://localhost:5173}") List<String> origenes) {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(origenes);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource fuente = new UrlBasedCorsConfigurationSource();
        fuente.registerCorsConfiguration("/**", config);
        return fuente;
    }
}
