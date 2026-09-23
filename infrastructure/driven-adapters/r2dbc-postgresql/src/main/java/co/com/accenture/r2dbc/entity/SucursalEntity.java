package co.com.accenture.r2dbc.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("sucursal")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SucursalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column("id_sucursal")
    private Long idSucursal;

    @Column("nombre")
    private String nombre;

    @Column("id_franquicia")
    private Long idFranquicia;
}
