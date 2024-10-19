package org.restful.customer.customer.repository;

import io.swagger.v3.oas.annotations.media.Schema;
import org.restful.customer.customer.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade {@code Customer}.
 *
 * <p>Esta interface estende {@code MongoRepository}, fornecendo métodos CRUD
 * para a entidade {@code Customer}. O Spring Data MongoDB automaticamente
 * implementa esta interface, facilitando a interação com o banco de dados.
 *
 * <p>Operações disponíveis:
 * <ul>
 *   <li>Salvar um cliente.</li>
 *   <li>Buscar clientes por ID.</li>
 *   <li>Atualizar informações de um cliente.</li>
 *   <li>Deletar um cliente.</li>
 *   <li>Listar todos os clientes.</li>
 * </ul>
 *
 * @see Customer
 */
@Repository("customerRepository")
@Schema(description = "Repositório para a entidade Customer.")
public interface CustomerRepository extends MongoRepository<Customer, String> {
    // Métodos padrão do MongoRepository já são suficientes para operações CRUD básicas.
}
