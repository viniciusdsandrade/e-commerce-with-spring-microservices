package org.restful.customer.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
@Schema(description = "Exceção lançada quando um cliente não é encontrado no sistema.")
public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
