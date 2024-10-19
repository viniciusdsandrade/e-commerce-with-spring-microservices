package org.restful.customer.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.restful.customer.customer.entity.Address;
import org.restful.customer.customer.entity.Customer;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável por mapear entre DTOs e a entidade {@code Customer}.
 *
 * <p>Esta classe fornece métodos para converter objetos de requisição e resposta
 * (DTOs) para a entidade {@code Customer} e vice-versa. Facilita a transformação
 * de dados entre as diferentes camadas da aplicação.
 *
 * <p>Métodos disponíveis:
 * <ul>
 *   <li>{@code toCustomer(CustomerRequest)}: Converte um {@code CustomerRequest} em {@code Customer}.</li>
 *   <li>{@code toAddress(Address)}: Converte um {@code Address} em outro {@code Address}.</li>
 *   <li>{@code fromCustomer(Customer)}: Converte um {@code Customer} em {@code CustomerResponse}.</li>
 * </ul>
 *
 * @see CustomerRequest
 * @see CustomerResponse
 * @see Customer
 */
@Service("customerMapper")
@Schema(description = "Serviço responsável por mapear entre DTOs e a entidade Customer.")
public class CustomerMapper {

    /**
     * Converte um {@code CustomerRequest} em {@code Customer}.
     *
     * @param customerDto Dados de requisição do cliente.
     * @return Entidade {@code Customer} mapeada.
     */
    public Customer toCustomer(CustomerRequest customerDto) {
        if (customerDto == null) return null;

        return Customer.builder()
                .firstname(customerDto.firstName())
                .lastname(customerDto.lastName())
                .email(customerDto.email())
                .address(Address.builder()
                        .street(customerDto.address().getStreet())
                        .city(customerDto.address().getCity())
                        .state(customerDto.address().getState())
                        .zip(customerDto.address().getZip())
                        .country(customerDto.address().getCountry())
                        .build())
                .build();
    }

    /**
     * Converte um {@code Address} em outro {@code Address}.
     *
     * @param address Endereço a ser convertido.
     * @return Novo objeto {@code Address} mapeado.
     */
    public Address toAddress(Address address) {
        if (address == null) return null;

        return Address.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .zip(address.getZip())
                .country(address.getCountry())
                .build();
    }

    /**
     * Converte uma entidade {@code Customer} em {@code CustomerResponse}.
     *
     * @param customer Entidade {@code Customer} a ser convertida.
     * @return DTO {@code CustomerResponse} mapeado.
     */
    public CustomerResponse fromCustomer(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstname(),
                customer.getLastname(),
                customer.getEmail(),
                Address.builder()
                        .street(customer.getAddress().getStreet())
                        .city(customer.getAddress().getCity())
                        .state(customer.getAddress().getState())
                        .zip(customer.getAddress().getZip())
                        .country(customer.getAddress().getCountry())
                        .build()
        );
    }
}
