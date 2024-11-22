package co.edu.ucentral.paginavehiculos.vehiculos_usados.servicios;

import co.edu.ucentral.paginavehiculos.vehiculos_usados.persistencia.entidades.Usuario;
import co.edu.ucentral.paginavehiculos.vehiculos_usados.persistencia.entidades.Vehiculos;
import co.edu.ucentral.paginavehiculos.vehiculos_usados.persistencia.repositorios.VehiculosRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class VehiculosServicio {

    @Autowired
    VehiculosRepositorio vehiculosRepositorio;

    public void registrarVehiculo(Vehiculos vehiculo) {
        vehiculosRepositorio.save(vehiculo);
    }

    public Vehiculos obtenerVehiculoPorId(Long id) {
        return vehiculosRepositorio.findById(id).orElse(null);
    }

    public void actualizarVehiculo(Vehiculos vehiculo) {
        vehiculosRepositorio.save(vehiculo); // `save` actualiza si el objeto tiene un ID existente
    }

    public List<Vehiculos> obtenerTodosLosVehiculos() {
        return (List<Vehiculos>) vehiculosRepositorio.findAll();
    }

    // Obtener todos los vehículos
    public List<Vehiculos> obtenerTodos() {
        return (List<Vehiculos>) vehiculosRepositorio.findAll();
    }

    // Eliminar un vehículo
    public boolean borrarVehiculo(Long id) {
        try {
            // Verificar si el vehículo existe antes de intentar eliminarlo
            Vehiculos vehiculo = vehiculosRepositorio.findById(id).orElse(null);
            if (vehiculo == null) {
                return false; // El vehículo no existe
            }

            vehiculosRepositorio.delete(vehiculo);
            return true; // Vehículo eliminado exitosamente
        } catch (Exception e) {
            // Registrar el error para diagnóstico si es necesario
            e.printStackTrace();
            return false; // Error al eliminar el vehículo
        }
    }
    public List<Vehiculos> obtenerVehiculosPorUsuario(Usuario usuario) {
        return vehiculosRepositorio.findByUsuario(usuario);
    }
}
