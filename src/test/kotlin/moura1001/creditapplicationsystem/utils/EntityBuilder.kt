package moura1001.creditapplicationsystem.utils

import moura1001.creditapplicationsystem.domain.Address
import moura1001.creditapplicationsystem.domain.Customer
import moura1001.creditapplicationsystem.dto.CustomerDto
import moura1001.creditapplicationsystem.dto.CustomerUpdateDto
import java.math.BigDecimal

class EntityBuilder {
    companion object {
        fun buildCustomer(
            id: Long? = null,
            firstName: String = "First",
            lastName: String = "Last",
            cpf: String = "86155985090",
            email: String = "email@gmail.com",
            password: String = "1234",
            zipCode: String = "11222333",
            street: String = "Logo ali",
            income: BigDecimal = BigDecimal.ZERO,
        ) = Customer(
            id = id,
            firstName = firstName,
            lastName = lastName,
            cpf = cpf,
            email = email,
            password = password,
            address = Address(zipCode = zipCode, street = street),
            income = income
        )

        fun buildCustomerDto(
            firstName: String = "First",
            lastName: String = "Last",
            cpf: String = "86155985090",
            email: String = "email@gmail.com",
            password: String = "1234",
            zipCode: String = "11222333",
            street: String = "Logo ali",
            income: BigDecimal = BigDecimal.ZERO,
        ) = CustomerDto(
            firstName = firstName,
            lastName = lastName,
            cpf = cpf,
            email = email,
            password = password,
            zipCode = zipCode, street = street,
            income = income
        )

        fun buildCustomerUpdateDto(
            firstName: String = "FirstUpdated",
            lastName: String = "LastUpdated",
            zipCode: String = "44555666",
            street: String = "Lugar algum",
            income: BigDecimal = BigDecimal.valueOf(10000.0),
        ) = CustomerUpdateDto(
            firstName = firstName,
            lastName = lastName,
            zipCode = zipCode, street = street,
            income = income
        )
    }
}