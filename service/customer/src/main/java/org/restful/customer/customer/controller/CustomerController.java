package org.restful.customer.customer.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.restful.customer.customer.dto.CustomerRequest;
import org.restful.customer.customer.dto.CustomerResponse;
import org.restful.customer.customer.service.CustomerService;
import org.restful.customer.customer.service.impl.CustomerServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

/**
 * Controlador REST para gerenciar operações relacionadas a clientes.
 *
 * <p>Esta classe define os endpoints da API para criar, atualizar, buscar, verificar existência e deletar clientes.
 * Utiliza o serviço {@code CustomerService} para realizar as operações de negócio.
 *
 * <p>Endpoints disponíveis:
 * <ul>
 *   <li>{@code POST /api/v1/customer}: Cria um novo cliente.</li>
 *   <li>{@code PUT /api/v1/customer}: Atualiza informações de um cliente existente.</li>
 *   <li>{@code GET /api/v1/customer}: Retorna a lista de todos os clientes.</li>
 *   <li>{@code GET /api/v1/customer/exits/{customer-id}}: Verifica se um cliente existe.</li>
 *   <li>{@code GET /api/v1/customer/{customer-id}}: Retorna informações de um cliente específico.</li>
 *   <li>{@code DELETE /api/v1/customer/{customer-id}}: Deleta um cliente específico.</li>
 * </ul>
 *
 * @see CustomerService
 * @see CustomerServiceImpl
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
@Schema(description = "Controlador REST para gerenciar operações relacionadas a clientes")
@Tag(name = "Customer Controller", description = "Controller para gerenciamento de clientes")
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Cria um novo cliente.
     *
     * @param customerDto Dados do cliente a ser criado.
     * @return Identificador do cliente criado.
     */
    @Operation(summary = "Criar um novo cliente", description = "Cria um novo cliente com os dados fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de cliente inválidos.")
    })
    @PostMapping
    public ResponseEntity<String> createCustomer(
            @RequestBody @Valid CustomerRequest customerDto
    ) {
        return ok(customerService.createCustomer(customerDto));
    }

    /**
     * Atualiza informações de um cliente existente.
     *
     * @param customerDto Dados do cliente a serem atualizados.
     * @return Resposta vazia indicando que a atualização foi aceita.
     */
    @Operation(summary = "Atualizar um cliente", description = "Atualiza as informações de um cliente existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Atualização do cliente aceita."),
            @ApiResponse(responseCode = "400", description = "Dados de cliente inválidos."),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    })
    @PutMapping
    public ResponseEntity<Void> updateCustomer(
            @RequestBody @Valid CustomerRequest customerDto
    ) {
        customerService.updateCustomer(customerDto);
        return accepted().build();
    }

    /**
     * Retorna a lista de todos os clientes.
     *
     * @return Lista de clientes.
     */
    @Operation(summary = "Listar todos os clientes", description = "Retorna uma lista completa de todos os clientes cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso.")
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getCustomer() {
        return ok(customerService.findAllCustomers());
    }

    /**
     * Verifica se um cliente existe com o ID fornecido.
     *
     * @param customerId Identificador do cliente.
     * @return {@code true} se o cliente existir, {@code false} caso contrário.
     */
    @Operation(summary = "Verificar existência de um cliente", description = "Verifica se um cliente com o ID fornecido existe no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente existe ou não."),
            @ApiResponse(responseCode = "400", description = "ID de cliente inválido.")
    })
    @GetMapping("/exits/{customer-id}")
    public ResponseEntity<Boolean> customerExists(
            @PathVariable("customer-id") String customerId
    ) {
        return ok(customerService.existsById(customerId));
    }

    /**
     * Retorna informações de um cliente específico.
     *
     * @param customerId Identificador do cliente.
     * @return Informações detalhadas do cliente.
     */
    @Operation(summary = "Obter informações de um cliente", description = "Retorna as informações detalhadas de um cliente específico com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Informações do cliente retornadas com sucesso."),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    })
    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> getCustomer(
            @PathVariable("customer-id") String customerId
    ) {
        return ok(customerService.findCustomerById(customerId));
    }

    /**
     * Deleta um cliente específico.
     *
     * @param customerId Identificador do cliente a ser deletado.
     * @return Resposta vazia indicando que a deleção foi bem-sucedida.
     */
    @Operation(summary = "Deletar um cliente", description = "Remove um cliente específico do sistema com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    })
    @DeleteMapping("/{customer-id}")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable("customer-id") String customerId
    ) {
        customerService.deleteCustomer(customerId);
        return noContent().build();
    }
}
