package co.edu.ucentral.paginavehiculos.vehiculos_usados.controladores;

import co.edu.ucentral.paginavehiculos.vehiculos_usados.persistencia.entidades.Vehiculos;
import co.edu.ucentral.paginavehiculos.vehiculos_usados.servicios.VehiculosServicio;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@AllArgsConstructor
@Controller
public class VehiculosControlador {

    @Autowired
    VehiculosServicio vehiculosServicio;

    // Carga el formulario de registro de vehículo
    @GetMapping("/registro-vehiculo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("vehiculo", new Vehiculos());
        return "registro-vehiculo";
    }

    // Maneja el envío del formulario y guarda el vehículo
    @PostMapping("/almacenar-vehiculo")
    public String registrarVehiculo(@ModelAttribute("elvehiculo") Vehiculos vehiculo, Model model) {
        vehiculosServicio.registrarVehiculo(vehiculo);
        model.addAttribute("mensajeExito", "¡Vehículo registrado exitosamente!"); // Agrega el mensaje de éxito
        model.addAttribute("elvehiculo", new Vehiculos()); // Limpiar el formulario
        model.addAttribute("vehiculos", vehiculosServicio.obtenerTodosLosVehiculos()); // Obtener y mostrar vehículos
        return "pantallaVendedor"; // Redirigir a la página principal del vendedor
    }

    // Método para mostrar la página inicial del vendedor con la lista de vehículos
    @GetMapping("/pantalla-vendedor")
    public String mostrarFormularioDeVendedor(Model model) {
        model.addAttribute("elvehiculo", new Vehiculos());
        model.addAttribute("vehiculos", vehiculosServicio.obtenerTodosLosVehiculos()); // Obtener y mostrar vehículos
        return "pantallaVendedor"; // Renderizar la página
    }

    // Página de vehículos registrados
    @GetMapping("/vehiculos-registrados")
    public String verVehiculosRegistrados(Model model) {
        model.addAttribute("vehiculos", vehiculosServicio.obtenerTodosLosVehiculos()); // Obtener y mostrar vehículos registrados
        return "vehiculosRegistrados"; // Redirigir a la vista de vehículos registrados
    }

    @GetMapping("/pantalla-comprador")
    public String mostrarFormularioDeComprador(Model model) {
        return "pantallaComprador"; // Renderizar la página
    }
}
