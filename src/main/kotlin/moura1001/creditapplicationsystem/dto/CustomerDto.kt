package moura1001.creditapplicationsystem.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import moura1001.creditapplicationsystem.domain.Address
import moura1001.creditapplicationsystem.domain.Customer
import org.hibernate.validator.constraints.br.CPF
import java.math.BigDecimal

data class CustomerDto(
    @field:NotEmpty val firstName: String = "",
    @field:NotEmpty val lastName: String = "",
    @field:NotEmpty @field:CPF val cpf: String = "",
    @field:NotEmpty @field:Email val email: String = "",
    @field:NotEmpty val password: String = "",
    @field:NotEmpty val zipCode: String = "",
    @field:NotEmpty val street: String = "",
    @field:NotNull @field:PositiveOrZero val income: BigDecimal = BigDecimal.ZERO
) {
    fun toEntity(): Customer {
        // validations

        return Customer(
            firstName = this.firstName,
            lastName = this.lastName,
            cpf = this.cpf,
            email = this.email,
            password = this.password,
            address = Address(zipCode = this.zipCode, street = this.street),
            income = this.income
        )
    }
}
