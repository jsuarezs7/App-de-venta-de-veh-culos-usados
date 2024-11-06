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
    VehiculosServicio VehiculosServicio;

    // Carga el formulario de registro de vehículo
    @GetMapping("/registro-vehiculo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("vehiculo", new Vehiculos());
        return "registro-vehiculo";
    }

    // Maneja el envío del formulario y guarda el vehículo
    @PostMapping("/almacenar-vehiculo")
    public String registrarVehiculo(@ModelAttribute("vehiculo") Vehiculos vehiculo, Model model) {
        VehiculosServicio.registrarVehiculo(vehiculo);
        model.addAttribute("mensaje", "Vehículo registrado exitosamente");
        return "registro-vehiculo"; // Retorna a la misma página después del registro
    }
    @GetMapping("/pantalla-vendedor")
    public String mostrarFormularioDeVendedor(Model model) {
        return "pantallaVendedor";
    }

}

