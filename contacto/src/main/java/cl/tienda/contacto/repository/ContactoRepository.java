package cl.tienda.contacto.repository;

import cl.tienda.contacto.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactoRepository extends JpaRepository<Contacto, Long> {

  // Los mensajes más nuevos primero (lo que ve el administrador)
    List<Contacto> findAllByOrderByFechaCreacionDesc();
}
