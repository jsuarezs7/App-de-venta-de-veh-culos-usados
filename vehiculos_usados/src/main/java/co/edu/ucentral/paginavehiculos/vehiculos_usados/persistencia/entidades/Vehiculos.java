
package co.edu.ucentral.paginavehiculos.vehiculos_usados.persistencia.entidades;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "vehiculos")
@Entity
public class Vehiculos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "veh_id")
    private Long id;

    @Column(name = "veh_marca", nullable = false)
    private String marca;

    @Column(name = "veh_modelo", nullable = false)
    private String modelo;

    @Column(name = "veh_fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "veh_kilometraje", nullable = false)
    private int kilometraje;

    @Column(name = "veh_precio", nullable = false)
    private double precio;

    @Column(name = "veh_descripcion", nullable = true, length = 500)
    private String descripcion;

}
