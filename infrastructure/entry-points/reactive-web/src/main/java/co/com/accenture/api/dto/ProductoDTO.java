package co.com.accenture.api.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductoDTO {

    private Long idProducto;
    private String nombre;
    private Integer cantidad;
    private Long idSucursal;
}

