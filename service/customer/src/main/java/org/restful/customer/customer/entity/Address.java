package org.restful.customer.customer.entity;

import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * Representa o endereço de um cliente.
 *
 * <p>Esta classe contém informações detalhadas sobre o endereço, incluindo rua, cidade, estado, CEP e país.
 * Utiliza anotações do Lombok para gerar automaticamente construtores, getters, setters e o padrão Builder.
 * A anotação {@code @Validated} é utilizada para garantir que as instâncias da classe atendam às restrições de validação.
 *
 * <p>Exemplo de uso:
 * <pre>
 * Address address = Address.builder()
 *     .street("Rua Exemplo")
 *     .city("São Paulo")
 *     .state("SP")
 *     .zip("01000-000")
 *     .country("Brasil")
 *     .build();
 * </pre>
 *
 * @see Customer
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Validated
public class Address {
    private String street;    // Rua do endereço
    private String city;      // Cidade do endereço
    private String state;     // Estado do endereço
    private String zip;       // CEP do endereço
    private String country;   // País do endereço
}
