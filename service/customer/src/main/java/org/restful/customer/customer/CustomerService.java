package org.restful.customer.customer;

import jakarta.validation.Valid;

import java.util.List;

public interface CustomerService  {
    String createCustomer(@Valid CustomerRequest customerDto);

    void updateCustomer(@Valid CustomerRequest customerDto);

    List<CustomerResponse> findAllCustomers();

    Boolean existsById(String customerId);

    CustomerResponse findCustomerById(String customerId);

    void deleteCustomer(String customerId);
}
