package moura1001.creditapplicationsystem.utils

import moura1001.creditapplicationsystem.domain.Address
import moura1001.creditapplicationsystem.domain.Credit
import moura1001.creditapplicationsystem.domain.Customer
import moura1001.creditapplicationsystem.dto.CreditDto
import moura1001.creditapplicationsystem.dto.CustomerDto
import moura1001.creditapplicationsystem.dto.CustomerUpdateDto
import java.math.BigDecimal
import java.time.LocalDate

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

        fun buildCredit(
            creditValue: BigDecimal = BigDecimal.valueOf(5000.0),
            dayFirstInstallment: LocalDate = LocalDate.now().plusDays(30),
            numberOfInstallments: Int = 5,
            customer: Customer
        ) = Credit(
            creditValue = creditValue,
            dayFirstInstallment = dayFirstInstallment,
            numberOfInstallments = numberOfInstallments,
            customer = customer
        )

        fun buildCreditDto(
            creditValue: BigDecimal = BigDecimal.valueOf(5000.0),
            dayFirstInstallment: LocalDate = LocalDate.now().plusDays(30),
            numberOfInstallments: Int = 5,
            customerId: Long = 1
        ) = CreditDto(
            creditValue = creditValue,
            dayFirstInstallment = dayFirstInstallment,
            numberOfInstallments = numberOfInstallments,
            customerId = customerId
        )
    }
}