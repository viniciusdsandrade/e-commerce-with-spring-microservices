package org.restful.customer.customer.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.restful.customer.customer.dto.CustomerRequest;
import org.restful.customer.customer.dto.CustomerResponse;
import org.restful.customer.customer.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
@Schema(description = "Controlador REST para gerenciar operações relacionadas a clientes")
@Tag(name = "Customer Controller", description = "Controller para gerenciamento de clientes")
public class CustomerController {

    private final CustomerService customerService;


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

    @Operation(summary = "Listar todos os clientes", description = "Retorna uma lista completa de todos os clientes cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso.")
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getCustomer() {
        return ok(customerService.findAllCustomers());
    }

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
