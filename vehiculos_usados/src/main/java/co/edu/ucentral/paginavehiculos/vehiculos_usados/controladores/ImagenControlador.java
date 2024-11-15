package co.edu.ucentral.paginavehiculos.vehiculos_usados.controladores;

import co.edu.ucentral.paginavehiculos.vehiculos_usados.persistencia.entidades.Imagen;
import co.edu.ucentral.paginavehiculos.vehiculos_usados.servicios.VehiculosServicio;
import co.edu.ucentral.paginavehiculos.vehiculos_usados.servicios.ImagenServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import java.io.IOException;
import java.util.Base64;

@Controller
public class ImagenControlador {

    @Autowired
    VehiculosServicio vehiculoServicio;

    @Autowired
    ImagenServicio imagenServicio;

    @PostMapping("/agregar-imagen")
    public String agregarImagen(@RequestParam("imagen") MultipartFile imagen,
                                @RequestParam("vehiculoId") int vehiculoId,
                                @RequestParam("descripcion") String descripcion,
                                Model model) {
        try {
            // Obtener el vehículo por su ID
            //Vehiculo vehiculo = vehiculoServicio.obtenerVehiculoPorId(vehiculoId);

            // Convertir la imagen a base64
            String imagenBase64 = Base64.getEncoder().encodeToString(imagen.getBytes());

            // Crear la entidad Imagen
            Imagen nuevaImagen = Imagen.builder()
                    .imagen64(imagenBase64)
                    .descripcion(descripcion)
                    //.vehiculo(vehiculo)
                    .build();

            // Guardar la imagen
            imagenServicio.guardarImagen(nuevaImagen);

            model.addAttribute("mensaje", "Imagen agregada con éxito");
        } catch (IOException e) {
            model.addAttribute("mensaje", "Error al cargar la imagen");
        }
        return "redirect:/mostrarFormularioDeVendedor"; // Redirigir a la página de gestión de vehículos
    }
}
