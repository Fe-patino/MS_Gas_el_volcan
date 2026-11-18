package ms.autenticacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ms.autenticacion.model.Usuario;
import java.util.List;
import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar usuario por correo (para login)
    Optional<Usuario>findByCorreo(String correo);

    //verificar si ya existe un correo registrado

    boolean existsByCorreo(String correo);


}
