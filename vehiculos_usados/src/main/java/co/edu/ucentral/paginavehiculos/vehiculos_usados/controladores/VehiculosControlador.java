    package co.edu.ucentral.paginavehiculos.vehiculos_usados.controladores;

    import co.edu.ucentral.paginavehiculos.vehiculos_usados.persistencia.entidades.Usuario;
    import co.edu.ucentral.paginavehiculos.vehiculos_usados.persistencia.entidades.Vehiculos;
    import co.edu.ucentral.paginavehiculos.vehiculos_usados.servicios.VehiculosServicio;
    import jakarta.servlet.http.HttpServletRequest;
    import lombok.AllArgsConstructor;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;

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
                    return "PantallaVendedor";
                }

                return "PantallaVendedor";
            } catch (Exception e) {
                model.addAttribute("mensaje", "Error al registrar el vehículo: " + e.getMessage());
                return "PantallaVendedor";
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

        // Cargar el vehículo para la edición
        @GetMapping("/editar-vehiculo/{id}")
        public String cargarVehiculoParaEdicion(@PathVariable Long id, Model model) {
            Vehiculos vehiculo = vehiculosServicio.obtenerVehiculoPorId(id);
            if (vehiculo == null) {
                model.addAttribute("mensaje", "Vehículo no encontrado.");
                return "redirect:/ver-vehiculos";
            }
            model.addAttribute("elvehiculo", vehiculo);
            return "VehiculosRegistrados"; // Aquí podrías retornar el formulario de edición
        }

        // Editar un vehículo
        @PostMapping("/editar-vehiculo")
        public String editarVehiculo(@ModelAttribute("elvehiculo") Vehiculos vehiculo, Model model, HttpServletRequest request) {
            try {
                vehiculosServicio.actualizarVehiculo(vehiculo);
                model.addAttribute("mensaje", "Vehículo actualizado exitosamente");

                // Obtener el usuario logueado desde la sesión
                Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuario");

                // Mostrar solo los vehículos del vendedor logueado
                model.addAttribute("vehiculos", vehiculosServicio.obtenerVehiculosPorUsuario(usuarioLogueado));
            } catch (Exception e) {
                model.addAttribute("mensaje", "Error al actualizar el vehículo: " + e.getMessage());
            }

            return "VehiculosRegistrados";
        }

        // Eliminar un vehículo
        @GetMapping("/eliminar-vehiculo/{id}")
        public String eliminarVehiculo(@PathVariable Long id, Model model, HttpServletRequest request) {
            Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuario");

            if (usuarioLogueado != null && vehiculosServicio.validarPropiedadVehiculo(id, usuarioLogueado)) {
                boolean eliminado = vehiculosServicio.borrarVehiculo(id);
                model.addAttribute("mensaje", eliminado ? "Vehículo eliminado exitosamente" : "Error al eliminar el vehículo");
            } else {
                model.addAttribute("mensaje", "No tienes permiso para eliminar este vehículo.");
            }

            return "redirect:/ver-vehiculos";
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

        @PostMapping("/comprar-vehiculo")
        public String comprarVehiculo(@RequestParam("idVehiculo") Long idVehiculo, Model model, HttpServletRequest request) {
            Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuario");

            if (usuarioLogueado == null) {
                model.addAttribute("mensaje", "Debes iniciar sesión para realizar una compra.");
                return "PantallaComprador";
            }


            boolean eliminado = vehiculosServicio.borrarVehiculo(idVehiculo);

            if (eliminado) {
                model.addAttribute("mensaje", "Compra realizada con éxito ☺.");
            } else {
                model.addAttribute("mensaje", "Error al realizar la compra. Inténtalo de nuevo.");
            }

            // Recargar los vehículos disponibles
            List<Vehiculos> vehiculos = vehiculosServicio.obtenerTodosLosVehiculos();
            model.addAttribute("vehiculos", vehiculos);

            return "PantallaComprador";
        }
    }
