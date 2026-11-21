package cl.tienda.contacto.service;

import cl.tienda.contacto.model.Contacto;
import cl.tienda.contacto.repository.ContactoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Set;

@Service
public class ContactoService {

    private static final Set<String> ESTADOS = Set.of("PENDIENTE", "RESPONDIDO");

    private final ContactoRepository repository;

    public ContactoService(ContactoRepository repository) {
        this.repository = repository;
    }

    public Contacto guardar(Contacto contacto) {
        contacto.setId(null);
        contacto.setEstado("PENDIENTE");
        return repository.save(contacto);
    }

    public List<Contacto> listar() {
        return repository.findAllByOrderByFechaCreacionDesc();
    }

    public Contacto cambiarEstado(Long id, String estado) {
        if (!ESTADOS.contains(estado)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado inválido: use PENDIENTE o RESPONDIDO");
        }
        Contacto c = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contacto no encontrado: " + id));
        c.setEstado(estado);
        return repository.save(c);
    }
}
