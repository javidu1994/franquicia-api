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
public class FranquiciaUpdateDTO {

    @NotBlank(message = "Nombre es requerido")
    @Size(max = 255)
    private String nombre;

}
