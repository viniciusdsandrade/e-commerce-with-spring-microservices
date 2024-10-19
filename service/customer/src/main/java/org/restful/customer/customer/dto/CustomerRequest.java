package org.restful.customer.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.restful.customer.customer.entity.Address;

/**
 * DTO para requisições de criação e atualização de clientes.
 *
 * <p>Esta classe encapsula os dados necessários para criar ou atualizar um cliente,
 * incluindo nome, sobrenome, email e endereço. Utiliza anotações de validação
 * para garantir que os campos obrigatórios sejam fornecidos e que o email seja válido.
 *
 * <p>Exemplo de uso:
 * <pre>
 * CustomerRequest request = new CustomerRequest(
 *     "12345",
 *     "João",
 *     "Silva",
 *     "joao.silva@example.com",
 *     address
 * );
 * </pre>
 *
 * @param id        Identificador do cliente (opcional na criação).
 * @param firstName Primeiro nome do cliente (obrigatório).
 * @param lastName  Sobrenome do cliente (obrigatório).
 * @param email     E-mail do cliente (obrigatório e deve ser válido).
 * @param address   Endereço do cliente.
 */
@Schema(description = "DTO para requisições de criação e atualização de clientes.")
public record CustomerRequest(
        String id,
        @NotNull(message = "First name is required")
        String firstName,
        @NotNull(message = "Last name is required")
        String lastName,
        @NotNull(message = "Email is required")
        @Email(message = "Email is invalid")
        String email,
        Address address
) {
}
