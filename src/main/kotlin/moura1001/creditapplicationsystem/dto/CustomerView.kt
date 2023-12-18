package moura1001.creditapplicationsystem.dto

import moura1001.creditapplicationsystem.domain.Customer
import java.math.BigDecimal

data class CustomerView(
    val firstName: String = "",
    val lastName: String = "",
    val cpf: String = "",
    val email: String = "",
    val zipCode: String = "",
    val street: String = "",
    val income: BigDecimal = BigDecimal.ZERO
) {
    constructor(customer: Customer) : this(
        firstName = customer.firstName,
        lastName = customer.lastName,
        cpf = customer.cpf,
        email = customer.email,
        zipCode = customer.address.zipCode,
        street = customer.address.street,
        income = customer.income
    )
}
