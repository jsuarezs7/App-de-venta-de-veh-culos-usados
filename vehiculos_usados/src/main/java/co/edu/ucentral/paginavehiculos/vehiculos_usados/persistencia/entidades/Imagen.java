package co.edu.ucentral.paginavehiculos.vehiculos_usados.persistencia.entidades;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table (name = "vehiculos_imagenes")
public class Imagen {
    @Id
    private int codigo;

}
