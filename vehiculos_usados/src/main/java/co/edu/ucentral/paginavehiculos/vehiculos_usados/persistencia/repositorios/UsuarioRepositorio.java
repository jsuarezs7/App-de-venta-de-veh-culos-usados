package co.edu.ucentral.paginavehiculos.vehiculos_usados.persistencia.repositorios;

import co.edu.ucentral.paginavehiculos.vehiculos_usados.persistencia.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UsuarioRepositorio extends CrudRepository<Usuario, String> {
    Optional<Usuario> findByUsuario(String usuario);
}
