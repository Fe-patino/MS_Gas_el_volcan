package cl.tienda.pagos.security;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Agrega el botón "Authorize" en Swagger para probar los endpoints protegidos pegando el token JWT
@Configuration
public class ConfiguracionOpenApi {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info().title("ms-pagos – Gas El Volcán").version("1.0"))
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
