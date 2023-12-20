package moura1001.creditapplicationsystem.controller

import com.fasterxml.jackson.databind.ObjectMapper
import moura1001.creditapplicationsystem.domain.Customer
import moura1001.creditapplicationsystem.domain.Status
import moura1001.creditapplicationsystem.repository.CreditRepository
import moura1001.creditapplicationsystem.repository.CustomerRepository
import moura1001.creditapplicationsystem.utils.EntityBuilder
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDate

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
class CreditControllerTest {
    @Autowired private lateinit var creditRepository: CreditRepository
    @Autowired private lateinit var customerRepository: CustomerRepository
    @Autowired private lateinit var mockMvc: MockMvc
    @Autowired private lateinit var objectMapper: ObjectMapper

    companion object {
        const val URL: String = "/api/credits"
    }

    private lateinit var customer: Customer

    @BeforeEach
    fun setUp() {
        creditRepository.deleteAll()
        customerRepository.deleteAll()
        customer = customerRepository.save(EntityBuilder.buildCustomer())
    }

    @Test
    fun `should save a credit and return http 201 status`() {
        // given
        val creditDto = EntityBuilder.buildCreditDto(customerId = customer.id!!)
        val valueAsString = objectMapper.writeValueAsString(creditDto)

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(valueAsString))
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.creditCode").isNotEmpty)
            .andExpect(MockMvcResultMatchers.jsonPath("$.creditValue").value(creditDto.creditValue))
            .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfInstallments").value(creditDto.numberOfInstallments))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(Status.IN_PROGRESS.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.emailCustomer").value(customer.email))
    }

    @Test
    fun `should not save a credit with invalid numberOfInstallments and return http 400 status`() {
        // given
        val creditDto = EntityBuilder.buildCreditDto(customerId = customer.id!!, numberOfInstallments = 49)
        val valueAsString = objectMapper.writeValueAsString(creditDto)

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(valueAsString))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class org.springframework.web.bind.MethodArgumentNotValidException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
    }

    @Test
    fun `should not save a credit with invalid dayFirstInstallment and return http 400 status`() {
        // given
        val creditDto = EntityBuilder.buildCreditDto(customerId = customer.id!!, dayFirstInstallment = LocalDate.now().plusMonths(3).plusDays(1))
        val valueAsString = objectMapper.writeValueAsString(creditDto)

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(valueAsString))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class moura1001.creditapplicationsystem.exception.BusinessException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
    }

    @Test
    fun `should find specific credit from customer and return http 200 status`() {
        // given
        val credit = creditRepository.save(EntityBuilder.buildCredit(customer = customer))

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("$URL/${credit.creditCode}?customerId=${customer.id}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.creditCode").value(credit.creditCode.toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.creditValue").value(credit.creditValue))
            .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfInstallments").value(credit.numberOfInstallments))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(Status.IN_PROGRESS.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.emailCustomer").value(customer.email))
    }

    @Test
    fun `should find all credits from customer and return http 200 status`() {
        // given
        creditRepository.save(EntityBuilder.buildCredit(customer = customer))
        creditRepository.save(EntityBuilder.buildCredit(customer = customer))

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("$URL?customerId=${customer.id}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize<Array<Any>>(2)))
            .andDo(MockMvcResultHandlers.print())
    }
}