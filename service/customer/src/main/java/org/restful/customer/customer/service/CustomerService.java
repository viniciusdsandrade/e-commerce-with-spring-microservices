package org.restful.customer.customer.service;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.restful.customer.customer.dto.CustomerRequest;
import org.restful.customer.customer.dto.CustomerResponse;
import org.restful.customer.customer.service.impl.CustomerServiceImpl;

import java.util.List;

@Schema(description = "Interface de serviço para operações relacionadas a clientes.")
public interface CustomerService  {

    String createCustomer(@Valid CustomerRequest customerDto);
    void updateCustomer(@Valid CustomerRequest customerDto);
    List<CustomerResponse> findAllCustomers();
    Boolean existsById(String customerId);
    CustomerResponse findCustomerById(String customerId);
    void deleteCustomer(String customerId);
}
