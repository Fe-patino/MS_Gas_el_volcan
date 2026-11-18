package cl.tienda.contacto.repository;

import cl.tienda.contacto.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactoRepository extends JpaRepository<Contacto, Long> {
}