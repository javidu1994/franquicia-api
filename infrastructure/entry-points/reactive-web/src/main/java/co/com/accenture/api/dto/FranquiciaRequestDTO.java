package co.com.accenture.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class FranquiciaRequestDTO {

    @NotBlank(message = "Nombre es requerido")
    @Size(max = 255)
    private String nombre;
}
