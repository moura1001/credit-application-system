package moura1001.creditapplicationsystem.repository

import moura1001.creditapplicationsystem.domain.Credit
import moura1001.creditapplicationsystem.domain.Customer
import moura1001.creditapplicationsystem.utils.EntityBuilder
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditRepositoryTest {
    @Autowired
    lateinit var creditRepository: CreditRepository

    @Autowired
    lateinit var testEntityManager: TestEntityManager

    private lateinit var customer: Customer
    private lateinit var credit1: Credit
    private lateinit var credit2: Credit

    @BeforeEach
    fun setUp() {
        customer = testEntityManager.persist(EntityBuilder.buildCustomer())
        credit1 = testEntityManager.persist(EntityBuilder.buildCredit(customer = customer))
        credit2 = testEntityManager.persist(EntityBuilder.buildCredit(customer = customer))
    }

    @Test
    fun `should find credit by credit code`() {
        // given
        val customerId = customer.id!!
        val creditCode1 = UUID.fromString(credit1.creditCode.toString())
        val creditCode2 = UUID.fromString(credit2.creditCode.toString())

        // when
        val fakeCredit1 = creditRepository.findByCustomerIdAndCreditCode(customerId, creditCode1)
        val fakeCredit2 = creditRepository.findByCustomerIdAndCreditCode(customerId, creditCode2)

        // then
        assertThat(fakeCredit1.isPresent).isTrue
        assertThat(fakeCredit1.get()).isSameAs(credit1)
        assertThat(fakeCredit2.isPresent).isTrue
        assertThat(fakeCredit2.get()).isSameAs(credit2)
    }

    @Test
    fun `should find all credits by customer id`() {
        // given
        val customerId = 1L

        // when
        val creditList = creditRepository.findAllCredits(customerId)

        // then
        assertThat(creditList).isNotEmpty
        assertThat(creditList.size).isEqualTo(2)
        assertThat(creditList).contains(credit1, credit2)
    }
}