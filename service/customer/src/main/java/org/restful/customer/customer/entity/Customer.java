package org.restful.customer.customer.entity;

import lombok.*;
import org.restful.customer.customer.repository.CustomerRepository;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Representa um cliente no sistema.
 *
 * <p>Esta classe contém informações detalhadas sobre um cliente, incluindo nome, sobrenome, email e endereço.
 * É mapeada para um documento no MongoDB utilizando a anotação {@code @Document}.
 *
 * <p>O campo {@code id} é utilizado como identificador único no banco de dados.
 *
 * <p>Exemplo de uso:
 * <pre>
 * Customer customer = Customer.builder()
 *     .id("12345")
 *     .firstname("João")
 *     .lastname("Silva")
 *     .email("joao.silva@example.com")
 *     .address(address)
 *     .build();
 * </pre>
 *
 * @see Address
 * @see CustomerRepository
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class Customer {

    @Id
    private String id;            // Identificador único do cliente no MongoDB
    private String firstname;     // Primeiro nome do cliente
    private String lastname;      // Sobrenome do cliente
    private String email;         // E-mail do cliente
    private Address address;      // Endereço do cliente
}
