package ms.autenticacion.dto;

import lombok.Data;

@Data 
public class SolicitudLogin {
    private String correo;
    private String password;

}
