package moura1001.creditapplicationsystem.dto

import moura1001.creditapplicationsystem.domain.Address
import moura1001.creditapplicationsystem.domain.Customer
import java.math.BigDecimal

data class CustomerUpdateDto(
    val firstName: String = "",
    val lastName: String = "",
    val zipCode: String = "",
    val street: String = "",
    val income: BigDecimal = BigDecimal.ZERO
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
