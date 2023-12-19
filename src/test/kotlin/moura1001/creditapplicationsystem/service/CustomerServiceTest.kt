package moura1001.creditapplicationsystem.service

import moura1001.creditapplicationsystem.domain.Address
import moura1001.creditapplicationsystem.domain.Customer
import moura1001.creditapplicationsystem.exception.BusinessException
import moura1001.creditapplicationsystem.repository.CustomerRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
//import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.util.*

@ActiveProfiles("test")
@ExtendWith(MockitoExtension::class)
class CustomerServiceTest {
    @Mock
    lateinit var customerRepository: CustomerRepository

    @InjectMocks
    lateinit var customerService: CustomerService

    @Test
    fun `should create customer`() {
        // given
        val fakeCustomer: Customer = buildCustomer()
        Mockito.`when`(customerRepository.save(Mockito.any(Customer::class.java))).thenReturn(fakeCustomer)

        // when
        val result: Customer = customerService.save(fakeCustomer)

        // then
        assertThat(result).isNotNull
        assertThat(result).isSameAs(fakeCustomer)
        Mockito.verify(customerRepository).save(fakeCustomer)
    }

    @Test
    fun `should find customer by id`() {
        // given
        val fakeId = Random().nextLong()
        val fakeCustomer: Customer = buildCustomer(id = fakeId)
        Mockito.`when`(customerRepository.findById(fakeId)).thenReturn(Optional.of(fakeCustomer))

        // when
        val result: Customer = customerService.findById(fakeId)

        // then
        assertThat(result).isNotNull
        assertThat(result).isExactlyInstanceOf(Customer::class.java)
        assertThat(result).isSameAs(fakeCustomer)
        Mockito.verify(customerRepository).findById(fakeId)
    }

    @Test
    fun `should not find customer by invalid id and throw BusinessException`() {
        // given
        val fakeId = Random().nextLong()
        Mockito.`when`(customerRepository.findById(fakeId)).thenReturn(Optional.empty())

        // when
        // then
        assertThatExceptionOfType(BusinessException::class.java)
            .isThrownBy { customerService.findById(fakeId) }
            .withMessage("customer $fakeId not found")
    }

    @Test
    fun `should delete customer by id`() {
        // given
        val fakeId = Random().nextLong()
        val fakeCustomer: Customer = buildCustomer(id = fakeId)
        Mockito.`when`(customerRepository.findById(fakeId)).thenReturn(Optional.of(fakeCustomer))
        Mockito.doNothing().`when`(customerRepository).delete(fakeCustomer)

        // when
        // then
        assertDoesNotThrow { customerService.delete(fakeId) }
    }

    private fun buildCustomer(
        id: Long = 1L,
        firstName: String = "",
        lastName: String = "",
        cpf: String = "",
        email: String = "",
        password: String = "",
        zipCode: String = "",
        street: String = "",
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
}