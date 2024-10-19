package org.restful.customer.customer.service.impl;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.restful.customer.customer.dto.CustomerMapper;
import org.restful.customer.customer.dto.CustomerRequest;
import org.restful.customer.customer.dto.CustomerResponse;
import org.restful.customer.customer.entity.Customer;
import org.restful.customer.customer.repository.CustomerRepository;
import org.restful.customer.customer.service.CustomerService;
import org.restful.customer.exception.CustomerNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

/**
 * Implementação da interface {@code CustomerService}.
 *
 * <p>Esta classe fornece a lógica de negócio para gerenciar clientes, incluindo criação,
 * atualização, busca, verificação de existência e deleção de clientes. Utiliza o
 * {@code CustomerRepository} para interagir com o banco de dados e o {@code CustomerMapper}
 * para converter entre DTOs e entidades.
 *
 * <p>Essa implementação garante que as operações sejam realizadas de forma consistente
 * e que as exceções apropriadas sejam lançadas em caso de erros.
 *
 * @see CustomerService
 * @see CustomerRepository
 * @see CustomerMapper
 */
@Service("customerService")
@RequiredArgsConstructor
@Schema(description = "Implementação da interface CustomerService.")
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;  // Repositório para operações de banco de dados
    private final CustomerMapper customerMapper;          // Mapper para conversão entre DTOs e entidades

    /**
     * Cria um novo cliente.
     *
     * @param customerDto Dados do cliente a ser criado.
     * @return Identificador do cliente criado.
     */
    public String createCustomer(@Valid CustomerRequest customerDto) {
        Customer customer = customerMapper.toCustomer(customerDto);
        customerRepository.save(customer);
        return customer.getId();
    }

    /**
     * Atualiza informações de um cliente existente.
     *
     * @param customerDto Dados do cliente a serem atualizados.
     */
    @Override
    public void updateCustomer(
            @RequestBody @Valid CustomerRequest customerDto
    ) {
        var customer = customerRepository.findById(customerDto.id())
                .orElseThrow(() -> new CustomerNotFoundException(format("Customer with id %s not found", customerDto.id())));
        mergeCustomer(customer, customerDto);
        customerRepository.save(customer);
    }

    /**
     * Retorna a lista de todos os clientes.
     *
     * @return Lista de clientes.
     */
    @Override
    public List<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::fromCustomer)
                .toList();
    }

    /**
     * Verifica se um cliente existe com o ID fornecido.
     *
     * @param customerId Identificador do cliente.
     * @return {@code true} se o cliente existir, {@code false} caso contrário.
     */
    @Override
    public Boolean existsById(String customerId) {
        return customerRepository.findById(customerId)
                .isPresent();
    }

    /**
     * Retorna informações de um cliente específico.
     *
     * @param customerId Identificador do cliente.
     * @return Informações detalhadas do cliente.
     */
    @Override
    public CustomerResponse findCustomerById(String customerId) {
        return customerRepository.findById(customerId)
                .map(customerMapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException(format("Customer with id %s not found", customerId)));
    }

    /**
     * Deleta um cliente específico.
     *
     * @param customerId Identificador do cliente a ser deletado.
     */
    @Override
    public void deleteCustomer(String customerId) {
        customerRepository.deleteById(customerId);
    }

    /**
     * Mescla os dados de {@code customerDto} no {@code customer} existente.
     *
     * @param customer    Entidade {@code Customer} existente.
     * @param customerDto Dados de atualização do cliente.
     */
    private void mergeCustomer(Customer customer, @Valid CustomerRequest customerDto) {
        ofNullable(customerDto.firstName()).ifPresent(customer::setFirstname);
        ofNullable(customerDto.lastName()).ifPresent(customer::setLastname);
        ofNullable(customerDto.email()).ifPresent(customer::setEmail);
        ofNullable(customerDto.address()).ifPresent(address -> {
            ofNullable(address.getStreet()).ifPresent(customer.getAddress()::setStreet);
            ofNullable(address.getCity()).ifPresent(customer.getAddress()::setCity);
            ofNullable(address.getState()).ifPresent(customer.getAddress()::setState);
            ofNullable(address.getZip()).ifPresent(customer.getAddress()::setZip);
            ofNullable(address.getCountry()).ifPresent(customer.getAddress()::setCountry);
        });
        customerRepository.save(customer);
    }
}
