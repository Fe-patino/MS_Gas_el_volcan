package cl.tienda.novedades.service;

import cl.tienda.novedades.model.Novedad;
import cl.tienda.novedades.repository.NovedadRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NovedadService {

    private final NovedadRepository repository;

    public NovedadService(NovedadRepository repository) {
        this.repository = repository;
    }

    public List<Novedad> listar() {
        return repository.findAll();
    }

    public Novedad obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Novedad no encontrada: " + id));
    }

    public Novedad guardar(Novedad novedad) {
        return repository.save(novedad);
    }

    public Novedad actualizar(Long id, Novedad datos) {
        Novedad existente = obtenerPorId(id);
        existente.setTitulo(datos.getTitulo());
        existente.setDescripcion(datos.getDescripcion());
        existente.setImagen(datos.getImagen());
        existente.setFechaPublicacion(datos.getFechaPublicacion());
        return repository.save(existente);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}