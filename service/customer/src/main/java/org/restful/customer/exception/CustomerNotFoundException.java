package org.restful.customer.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Exceção lançada quando um cliente não é encontrado no sistema.
 *
 * <p>Esta exceção é utilizada para indicar que uma operação tentou acessar
 * um cliente inexistente, permitindo um tratamento de erros mais específico
 * e informativo.
 *
 * <p>Exemplo de uso:
 * <pre>
 * if (customer == null) {
 *     throw new CustomerNotFoundException("Cliente não encontrado com ID: " + customerId);
 * }
 * </pre>
 */
@ResponseStatus(NOT_FOUND)
@Schema(description = "Exceção lançada quando um cliente não é encontrado no sistema.")
public class CustomerNotFoundException extends RuntimeException {
    /**
     * Construtor da exceção {@code CustomerNotFoundException}.
     *
     * @param message Mensagem detalhando a razão da exceção.
     */
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
