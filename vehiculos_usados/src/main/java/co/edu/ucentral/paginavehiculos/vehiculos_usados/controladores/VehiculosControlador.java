package co.edu.ucentral.paginavehiculos.vehiculos_usados.controladores;

import co.edu.ucentral.paginavehiculos.vehiculos_usados.persistencia.entidades.Usuario;
import co.edu.ucentral.paginavehiculos.vehiculos_usados.persistencia.entidades.Vehiculos;
import co.edu.ucentral.paginavehiculos.vehiculos_usados.servicios.VehiculosServicio;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@AllArgsConstructor
@Controller
public class VehiculosControlador {

    @Autowired
    VehiculosServicio vehiculosServicio;

    /////////// PANTALLA VENDEDOR /////////////////
    // Maneja el envío del formulario y guarda el vehículo
    @PostMapping("/almacenar-vehiculo")
    public String registrarVehiculo(@ModelAttribute("elvehiculo") Vehiculos vehiculo, Model model, HttpServletRequest request) {
        try {
            // Obtener el usuario logueado desde la sesión
            Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuario");

            if (usuarioLogueado != null) {
                vehiculo.setUsuario(usuarioLogueado);  // Asociar el vehículo al usuario logueado
                vehiculosServicio.registrarVehiculo(vehiculo);
                model.addAttribute("mensaje", "Vehículo registrado exitosamente");
                model.addAttribute("elvehiculo", new Vehiculos());

                // Mostrar solo los vehículos del vendedor logueado
                model.addAttribute("vehiculos", vehiculosServicio.obtenerVehiculosPorUsuario(usuarioLogueado));
            } else {
                model.addAttribute("mensaje", "Debes iniciar sesión para registrar un vehículo.");
                return "pantallaVendedor";
            }

            return "pantallaVendedor";
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al registrar el vehículo: " + e.getMessage());
            return "pantallaVendedor";
        }
    }




    // Método para mostrar la página inicial del vendedor con la lista de vehículos
    @GetMapping("/pantalla-vendedor")
    public String mostrarFormularioDeVendedor(Model model, HttpServletRequest request) {
        // Obtener el usuario logueado de la sesión
        Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuario");

        if (usuarioLogueado != null) {
            // Obtener solo los vehículos del vendedor logueado
            model.addAttribute("vehiculos", vehiculosServicio.obtenerVehiculosPorUsuario(usuarioLogueado));
        }

        model.addAttribute("elvehiculo", new Vehiculos());
        return "pantallaVendedor"; // Renderizar la página
    }

    @GetMapping("/editar-vehiculo/{id}")
    public String cargarVehiculoParaEdicion(@PathVariable Long id, Model model) {
        Vehiculos vehiculo = vehiculosServicio.obtenerVehiculoPorId(id);
        if (vehiculo == null) {
            model.addAttribute("mensaje", "Vehículo no encontrado.");
            return "redirect:/pantalla-vendedor";
        }
        model.addAttribute("elvehiculo", vehiculo);
        return "pantallaVendedor";
    }

    @PostMapping("/editar-vehiculo")
    public String editarVehiculo(@ModelAttribute("elvehiculo") Vehiculos vehiculo, Model model) {
        vehiculosServicio.actualizarVehiculo(vehiculo);
        model.addAttribute("mensaje", "Vehículo actualizado exitosamente");
        model.addAttribute("vehiculos", vehiculosServicio.obtenerTodosLosVehiculos());
        return "pantallaVendedor";
    }
    // Eliminar un vehículo
    @GetMapping("/eliminar-vehiculo/{id}")
    public String eliminarVehiculo(@PathVariable Long id, Model model) {
        boolean eliminado = vehiculosServicio.borrarVehiculo(id);

        if (eliminado) {
            model.addAttribute("mensaje", "Vehículo eliminado exitosamente");
        } else {
            model.addAttribute("mensaje", "Error al eliminar el vehículo");
        }

        // Obtener la lista actualizada de vehículos
        model.addAttribute("vehiculos", vehiculosServicio.obtenerTodosLosVehiculos());
        return "redirect:/pantalla-vendedor"; // Volver a la página del vendedor con la lista actualizada
    }


    /////////// PANTALLA VENDEDOR /////////////////
    @GetMapping("/cerrar-sesion")
    public String cerrarSesion(HttpServletRequest request) {
        // Invalidar la sesión del usuario
        request.getSession().invalidate();

        // Redirigir a la página de inicio de sesión
        return "redirect:/inicio-sesion";
    }
    @GetMapping("/pantalla-comprador")
    public String mostrarFormularioDeComprador(Model model) {
        // Obtener todos los vehículos y pasarlos al modelo
        List<Vehiculos> vehiculos = vehiculosServicio.obtenerTodosLosVehiculos();
        model.addAttribute("vehiculos", vehiculos);

        return "pantallaComprador";
    }

    @GetMapping("/ver-vehiculos")
    public String mostrarVehiculosRegistrados(Model model) {
        List<Vehiculos> vehiculos = vehiculosServicio.obtenerTodosLosVehiculos();
        model.addAttribute("vehiculos", vehiculos);

        return "VehiculosRegistrados";
    }

}
