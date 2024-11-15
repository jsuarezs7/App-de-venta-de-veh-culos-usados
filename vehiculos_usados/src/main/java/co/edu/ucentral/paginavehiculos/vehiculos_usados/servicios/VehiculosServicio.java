package co.edu.ucentral.paginavehiculos.vehiculos_usados.servicios;

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
    VehiculosRepositorio VehiculosRepositorio;

    public void registrarVehiculo(Vehiculos vehiculo) {
        VehiculosRepositorio.save(vehiculo);
    }

    public List<Vehiculos> obtenerTodosLosVehiculos() {
        return (List<Vehiculos>) VehiculosRepositorio.findAll();
    }

    // Obtener todos los vehículos
    public List<Vehiculos> obtenerTodos() {
        return (List<Vehiculos>) VehiculosRepositorio.findAll();
    }

    // Eliminar un vehículo
    public boolean borrarVehiculo(Vehiculos vehiculo) {
        try {
            VehiculosRepositorio.delete(vehiculo);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
