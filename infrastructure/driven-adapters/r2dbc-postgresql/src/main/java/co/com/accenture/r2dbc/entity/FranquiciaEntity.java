package co.com.accenture.r2dbc.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("franquicia")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FranquiciaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column("id_franquicia")
    private Long idFranquicia;

    @Column("nombre")
    private String nombre;
}
