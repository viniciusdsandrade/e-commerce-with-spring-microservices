package org.restful.customer.customer.repository;

import io.swagger.v3.oas.annotations.media.Schema;
import org.restful.customer.customer.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("customerRepository")
@Schema(description = "Repositório para a entidade Customer.")
public interface CustomerRepository extends MongoRepository<Customer, String> {
}
