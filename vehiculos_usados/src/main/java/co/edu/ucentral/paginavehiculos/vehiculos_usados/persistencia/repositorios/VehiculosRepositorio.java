package co.edu.ucentral.paginavehiculos.vehiculos_usados.persistencia.repositorios;

import co.edu.ucentral.paginavehiculos.vehiculos_usados.persistencia.entidades.Vehiculos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface VehiculosRepositorio extends CrudRepository<Vehiculos, String> {

}
