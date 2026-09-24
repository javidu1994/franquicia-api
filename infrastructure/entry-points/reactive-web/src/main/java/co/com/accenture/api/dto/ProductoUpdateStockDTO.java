package co.com.accenture.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ProductoUpdateStockDTO {

    @NotNull(message = "Debe ingresar el producto a actualizar")
    private Long idProducto;

    @NotNull(message = "Debe ingresar la nueva cantidad del producto")
    @Min(value = 0, message = "La cantidad debe ser mayor o igual a 0")
    private Integer cantidad;

}
