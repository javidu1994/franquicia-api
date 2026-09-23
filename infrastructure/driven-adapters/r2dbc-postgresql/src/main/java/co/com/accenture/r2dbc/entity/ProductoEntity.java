package co.com.accenture.r2dbc.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("producto")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column("id_producto")
    private Long idProducto;

    @Column("nombre")
    private String nombre;

    @Column("cantidad")
    private Integer cantidad;

    @Column("id_sucursal")
    private Long idSucursal;
}
