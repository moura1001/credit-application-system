package moura1001.creditapplicationsystem.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import moura1001.creditapplicationsystem.domain.Address
import moura1001.creditapplicationsystem.domain.Customer
import java.math.BigDecimal

data class CustomerUpdateDto(
    @field:NotEmpty val firstName: String = "",
    @field:NotEmpty val lastName: String = "",
    @field:NotEmpty val zipCode: String = "",
    @field:NotEmpty val street: String = "",
    @field:NotNull @field:PositiveOrZero val income: BigDecimal = BigDecimal.ZERO
) {
    fun toEntity(customer: Customer): Customer {
        // validations

        val updatedCustomer = this

        return customer.apply {
            firstName = updatedCustomer.firstName
            lastName = updatedCustomer.lastName
            address.zipCode = updatedCustomer.zipCode
            address.street = updatedCustomer.street
            income = updatedCustomer.income
        }
    }
}
