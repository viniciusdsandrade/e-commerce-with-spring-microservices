package org.restful.customer.handler;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.ws.rs.BadRequestException;
import org.restful.customer.exception.CustomerNotFoundException;
import org.restful.customer.exception.DuplicateEntryException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;


@Schema(description = "GlobalExceptionHandler")
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    @Schema(description = "Manipula a exceção BadRequestException, lançada quando uma requisição malformada é recebida.")
    public ResponseEntity<List<ErrorDetails>> handleBadRequestException(
            BadRequestException exception,
            WebRequest webRequest
    ) {
        ErrorDetails errorDetails = new ErrorDetails(
                now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "BAD_REQUEST"
        );

        return new ResponseEntity<>(List.of(errorDetails), BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @Schema(description = "Manipula a exceção MethodArgumentNotValidException, lançada em caso de erros de validação.")
    public ResponseEntity<List<ValidationErrorDetails>> handleValidationException(
            MethodArgumentNotValidException exception,
            WebRequest request
    ) {
        List<ValidationErrorDetails> errors = new ArrayList<>();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errors.add(new ValidationErrorDetails(
                    now(),
                    error.getDefaultMessage(),
                    request.getDescription(false),
                    "METHOD_ARGUMENT_NOT_VALID_ERROR",
                    error.getField()
            ));
        }
        return ResponseEntity.status(BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @Schema(description = "Manipula a exceção IllegalArgumentException, lançada quando um argumento inválido é passado.")
    public ResponseEntity<List<ErrorDetails>> handleIllegalArgumentException(
            IllegalArgumentException exception,
            WebRequest webRequest
    ) {
        ErrorDetails errorDetails = new ErrorDetails(
                now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "INVALID_ARGUMENT"
        );

        return new ResponseEntity<>(List.of(errorDetails), BAD_REQUEST);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @Schema(description = "Manipula a exceção CustomerNotFoundException, lançada quando um cliente não é encontrado.")
    public ResponseEntity<List<ErrorDetails>> handleCustomerNotFoundException(
            CustomerNotFoundException exception,
            WebRequest webRequest
    ) {
        ErrorDetails errorDetails = new ErrorDetails(
                now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "CUSTOMER_NOT_FOUND"
        );

        return new ResponseEntity<>(List.of(errorDetails), NOT_FOUND);
    }

    @ExceptionHandler(DuplicateEntryException.class)
    @Schema(description = "Manipula a exceção DuplicateEntryException, lançada quando há uma tentativa de inserir uma entrada duplicada.")
    public ResponseEntity<List<ErrorDetails>> handleDuplicateEntryException(
            DuplicateEntryException exception,
            WebRequest webRequest
    ) {
        List<ErrorDetails> errors = new ArrayList<>();
        String[] mensagens = exception.getMessage().split("\\n");

        for (String mensagem : mensagens) {
            errors.add(new ErrorDetails(
                    now(),
                    mensagem.trim(),
                    webRequest.getDescription(false),
                    "DUPLICATE_ENTRY"
            ));
        }

        return ResponseEntity.status(CONFLICT).body(errors);
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    @Schema(description = "Manipula exceções genéricas, representando erros inesperados durante o processamento da requisição.")
    public ResponseEntity<List<ErrorDetails>> handleGlobalException(
            Exception exception,
            WebRequest webRequest
    ) {
        ErrorDetails errorDetails = new ErrorDetails(
                now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "INTERNAL_SERVER_ERROR"
        );

        return new ResponseEntity<>(List.of(errorDetails), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    @Schema(description = "Manipula exceções que indicam que uma funcionalidade não está implementada.")
    public ResponseEntity<List<ErrorDetails>> handleNotImplementedException(
            UnsupportedOperationException exception,
            WebRequest webRequest
    ) {
        ErrorDetails errorDetails = new ErrorDetails(
                now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "NOT_IMPLEMENTED"
        );

        return new ResponseEntity<>(List.of(errorDetails), NOT_IMPLEMENTED);
    }
}
