package co.com.accenture.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "routes.paths")
public class FranquiciaPath {

    private String franquicias;
    private String franquiciasById;

}
