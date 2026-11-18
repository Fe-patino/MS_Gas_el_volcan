package ms.autenticacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

// DTO para responder con el token JWT y datos del usuario
@Data 
@AllArgsConstructor 
public class RespuestaAuth {
    private String token;
    private String nombre;
    private String correo;
    private String rol;
    
}
