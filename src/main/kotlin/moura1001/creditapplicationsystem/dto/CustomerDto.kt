package moura1001.creditapplicationsystem.dto

import moura1001.creditapplicationsystem.domain.Address
import moura1001.creditapplicationsystem.domain.Customer
import java.math.BigDecimal

data class CustomerDto(
    val firstName: String = "",
    val lastName: String = "",
    val cpf: String = "",
    val email: String = "",
    val password: String = "",
    val zipCode: String = "",
    val street: String = "",
    val income: BigDecimal = BigDecimal.ZERO
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
