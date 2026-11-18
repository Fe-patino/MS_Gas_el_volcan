package ms.autenticacion.dto;

import lombok.Data;

//dto para recibir los datos de registro de usuario
@Data 
public class SolicitudRegistro {
    private String nombre;
    private String correo;
    private String password;
    private String rol; // cliente o admin
}
