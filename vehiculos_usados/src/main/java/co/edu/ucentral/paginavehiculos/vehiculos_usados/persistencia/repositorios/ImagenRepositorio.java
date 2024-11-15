package co.edu.ucentral.paginavehiculos.vehiculos_usados.persistencia.repositorios;

import co.edu.ucentral.paginavehiculos.vehiculos_usados.persistencia.entidades.Imagen;
import org.springframework.data.repository.CrudRepository;

public interface ImagenRepositorio extends CrudRepository <Imagen, Integer> {
}
