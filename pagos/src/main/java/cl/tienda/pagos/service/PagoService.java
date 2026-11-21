package cl.tienda.pagos.service;

import cl.tienda.pagos.model.Pago;
import cl.tienda.pagos.repository.PagoRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class PagoService {

    private final PagoRepository repository;

    public PagoService(PagoRepository repository) {
        this.repository = repository;
    }

    public List<Pago> listar() {
        return repository.findAll();
    }

    public List<Pago> listarPorCorreo(String correo) {
        return repository.findByCorreoCliente(correo);
    }

    // El correo del pago sale del token (no del cuerpo de la petición) para que nadie pague a nombre de otro
    public Pago registrar(Pago pago, String correoAutenticado) {
        if (pago.getNombreCliente() == null || pago.getNombreCliente().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre es obligatorio");
        }
        if (pago.getTotal() == null || pago.getTotal() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El total debe ser mayor a 0");
        }
        if (pago.getUltimosDigitos() == null || !pago.getUltimosDigitos().matches("\\d{4}")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los últimos dígitos deben ser 4 números");
        }
        pago.setId(null);
        pago.setCorreoCliente(correoAutenticado);
        pago.setEstado("COMPLETADO");
        return repository.save(pago);
    }
}
