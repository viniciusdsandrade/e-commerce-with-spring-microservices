package org.restful.customer.customer;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Service("customerService")
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public String createCustomer(@Valid CustomerRequest customerDto) {
        Customer customer = customerMapper.toCustomer(customerDto);
        customerRepository.save(customer);
        return customer.getId();
    }

    @Override
    public void updateCustomer(
            @RequestBody @Valid CustomerRequest customerDto
    ) {
        var customer = customerRepository.findById(customerDto.id())
                .orElseThrow(() -> new CustomerNotFoundException(format("Customer with id %s not found", customerDto.id())));
        mergeCustomer(customer, customerDto);
        customerRepository.save(customer);
    }

    @Override
    public List<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::fromCustomer)
                .toList();
    }

    @Override
    public Boolean existsById(String customerId) {
        return customerRepository.findById(customerId)
                .isPresent();
    }

    @Override
    public CustomerResponse findCustomerById(String customerId) {
        return customerRepository.findById(customerId)
                .map(customerMapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException(format("Customer with id %s not found", customerId)));
    }

    @Override
    public void deleteCustomer(String customerId) {
        customerRepository.deleteById(customerId);
    }

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
