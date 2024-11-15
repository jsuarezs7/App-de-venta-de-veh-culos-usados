package co.edu.ucentral.paginavehiculos.vehiculos_usados.servicios;

import co.edu.ucentral.paginavehiculos.vehiculos_usados.persistencia.entidades.Imagen;
import co.edu.ucentral.paginavehiculos.vehiculos_usados.persistencia.repositorios.ImagenRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImagenServicio {

    @Autowired
    private ImagenRepositorio imagenRepositorio;

    public void guardarImagen(Imagen imagen) {
        imagenRepositorio.save(imagen);
    }
}
