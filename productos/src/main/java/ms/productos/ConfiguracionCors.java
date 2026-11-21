package ms.productos;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Configuración CORS para permitir peticiones desde el frontend React
@Configuration
public class ConfiguracionCors implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")                          // Aplica a todos los endpoints
                .allowedOrigins("http://localhost:5173")    // URL del frontend React
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") // Métodos HTTP permitidos
                .allowedHeaders("*")                        // Permite todos los headers (incluido Authorization)
                .allowCredentials(true);                    // Permite envío de cookies y tokens
    }
}
