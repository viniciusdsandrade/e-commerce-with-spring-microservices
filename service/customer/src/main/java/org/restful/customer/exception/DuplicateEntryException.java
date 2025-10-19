package org.restful.customer.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(CONFLICT)
@Schema(description = "Exceção lançada quando uma entrada duplicada é detectada.")
public class DuplicateEntryException extends RuntimeException {
    public DuplicateEntryException(String message) {
        super(message);
    }
}