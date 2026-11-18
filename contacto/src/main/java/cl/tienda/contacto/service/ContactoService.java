package cl.tienda.contacto.service;

import cl.tienda.contacto.model.Contacto;
import cl.tienda.contacto.repository.ContactoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ContactoService {

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
        return repository.findAll();
    }

    public Contacto cambiarEstado(Long id, String estado) {
        Contacto c = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contacto no encontrado: " + id));
        c.setEstado(estado);
        return repository.save(c);
    }
}