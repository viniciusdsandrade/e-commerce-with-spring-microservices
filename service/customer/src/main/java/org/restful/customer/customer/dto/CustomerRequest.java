package org.restful.customer.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.restful.customer.customer.entity.Address;

@Schema(description = "DTO para requisições de criação e atualização de clientes.")
public record CustomerRequest(

        String id,

        @NotNull(message = "First name is required")
        String firstName,

        @NotNull(message = "Last name is required")
        String lastName,

        @NotNull(message = "Email is required")
        @Email(message = "Email is invalid")
        String email,

        Address address
) {
}
