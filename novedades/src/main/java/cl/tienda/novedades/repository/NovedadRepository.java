package cl.tienda.novedades.repository;

import cl.tienda.novedades.model.Novedad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NovedadRepository extends JpaRepository<Novedad, Long> {
}