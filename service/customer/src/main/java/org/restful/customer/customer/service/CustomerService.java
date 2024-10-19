package org.restful.customer.customer.service;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.restful.customer.customer.dto.CustomerRequest;
import org.restful.customer.customer.dto.CustomerResponse;
import org.restful.customer.customer.service.impl.CustomerServiceImpl;

import java.util.List;

/**
 * Interface de serviço para operações relacionadas a clientes.
 *
 * <p>Esta interface define os métodos de negócio necessários para criar, atualizar,
 * buscar, verificar existência e deletar clientes. A implementação desta interface
 * deve fornecer a lógica específica para cada operação.
 *
 * <p>Métodos disponíveis:
 * <ul>
 *   <li>{@code createCustomer(CustomerRequest)}: Cria um novo cliente.</li>
 *   <li>{@code updateCustomer(CustomerRequest)}: Atualiza informações de um cliente existente.</li>
 *   <li>{@code findAllCustomers()}: Retorna a lista de todos os clientes.</li>
 *   <li>{@code existsById(String)}: Verifica se um cliente existe por ID.</li>
 *   <li>{@code findCustomerById(String)}: Retorna informações de um cliente específico.</li>
 *   <li>{@code deleteCustomer(String)}: Deleta um cliente específico.</li>
 * </ul>
 *
 * @see CustomerServiceImpl
 */
@Schema(description = "Interface de serviço para operações relacionadas a clientes.")
public interface CustomerService  {
    /**
     * Cria um novo cliente.
     *
     * @param customerDto Dados do cliente a ser criado.
     * @return Identificador do cliente criado.
     */
    String createCustomer(@Valid CustomerRequest customerDto);

    /**
     * Atualiza informações de um cliente existente.
     *
     * @param customerDto Dados do cliente a serem atualizados.
     */
    void updateCustomer(@Valid CustomerRequest customerDto);

    /**
     * Retorna a lista de todos os clientes.
     *
     * @return Lista de clientes.
     */
    List<CustomerResponse> findAllCustomers();

    /**
     * Verifica se um cliente existe com o ID fornecido.
     *
     * @param customerId Identificador do cliente.
     * @return {@code true} se o cliente existir, {@code false} caso contrário.
     */
    Boolean existsById(String customerId);

    /**
     * Retorna informações de um cliente específico.
     *
     * @param customerId Identificador do cliente.
     * @return Informações detalhadas do cliente.
     */
    CustomerResponse findCustomerById(String customerId);

    /**
     * Deleta um cliente específico.
     *
     * @param customerId Identificador do cliente a ser deletado.
     */
    void deleteCustomer(String customerId);
}
