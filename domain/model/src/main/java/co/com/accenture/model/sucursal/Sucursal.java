package co.com.accenture.model.sucursal;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Sucursal {

    private Long idSucursal;
    private Long nombre;
}
