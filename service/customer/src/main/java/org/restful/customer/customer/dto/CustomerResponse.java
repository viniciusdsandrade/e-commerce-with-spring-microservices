package org.restful.customer.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.restful.customer.customer.entity.Address;

/**
 * DTO para respostas relacionadas a operações de clientes.
 *
 * <p>Esta classe encapsula as informações que serão retornadas ao cliente após
 * operações como criação, atualização ou busca de clientes. Inclui dados como
 * ID, nome, sobrenome, email e endereço.
 *
 * <p>Exemplo de uso:
 * <pre>
 * CustomerResponse response = new CustomerResponse(
 *     "12345",
 *     "João",
 *     "Silva",
 *     "joao.silva@example.com",
 *     address
 * );
 * </pre>
 *
 * @param id        Identificador do cliente.
 * @param firstName Primeiro nome do cliente.
 * @param lastName  Sobrenome do cliente.
 * @param email     E-mail do cliente.
 * @param address   Endereço do cliente.
 */
@Schema(description = "DTO para respostas relacionadas a operações de clientes.")
public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        Address address
) {
}
