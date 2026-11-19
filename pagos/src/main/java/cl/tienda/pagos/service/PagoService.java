package cl.tienda.pagos.service;

import cl.tienda.pagos.model.Pago;
import cl.tienda.pagos.repository.PagoRepository;
import org.springframework.stereotype.Service;
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

    public Pago registrar(Pago pago) {
        return repository.save(pago);
    }
}