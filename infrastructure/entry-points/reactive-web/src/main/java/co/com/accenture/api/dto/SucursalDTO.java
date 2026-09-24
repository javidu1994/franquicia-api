package co.com.accenture.api.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SucursalDTO {

    private Long idSucursal;
    private String nombre;
    private Long idFranquicia;
}

