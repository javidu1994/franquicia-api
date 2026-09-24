package co.com.accenture.api.dto;

import co.com.accenture.api.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ValidationErrorResponse extends BaseResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.FORMAT_DATE_TIME)
    private LocalDateTime createdDate;

    private List<ValidationError> errors;

    public ValidationErrorResponse() {
        super();
    }

    public ValidationErrorResponse(String message, ValidationError... errors) {
        this(message, Arrays.asList(errors));
    }

    public ValidationErrorResponse(String message, List<ValidationError> errors) {
        super(false, HttpStatus.BAD_REQUEST, message);
        this.errors = errors;
        this.createdDate = LocalDateTime.now();
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public void setErrors(List<ValidationError> errors) {
        this.errors = errors;
    }

}
