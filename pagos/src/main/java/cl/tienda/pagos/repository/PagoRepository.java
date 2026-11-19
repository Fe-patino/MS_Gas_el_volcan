package cl.tienda.pagos.repository;

import cl.tienda.pagos.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByCorreoCliente(String correoCliente);
}