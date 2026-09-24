package co.com.accenture.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationError {

    private String field;
    private String message;

    public ValidationError() {
        super();
    }

    public ValidationError(String message) {
        this(null, message);
    }

    public ValidationError(String field, String message) {
        this.field = field;
        this.message = message;
    }

}
