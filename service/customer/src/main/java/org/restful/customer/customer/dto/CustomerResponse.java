package org.restful.customer.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.restful.customer.customer.entity.Address;

@Schema(description = "DTO para respostas relacionadas a operações de clientes.")
public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        Address address
) {
}
