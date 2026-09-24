package co.com.accenture.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ProductoRequestDTO {

    @NotBlank(message = "Nombre es requerido")
    @Size(max = 255)
    private String nombre;

    @NotNull(message = "Debe ingresar una cantidad del producto")
    @Min(value = 0, message = "La cantidad debe ser mayor o igual a 0")
    private Integer cantidad;

    private Long idSucursal;
}
