package org.restful.customer.customer;

import org.springframework.stereotype.Service;

@Service("customerMapper")
public class CustomerMapper {
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
