package moura1001.creditapplicationsystem.controller

import com.fasterxml.jackson.databind.ObjectMapper
import moura1001.creditapplicationsystem.repository.CustomerRepository
import moura1001.creditapplicationsystem.utils.EntityBuilder
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
import java.math.BigDecimal

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
class CustomerControllerTest {
    @Autowired private lateinit var customerRepository: CustomerRepository
    @Autowired private lateinit var mockMvc: MockMvc
    @Autowired private lateinit var objectMapper: ObjectMapper

    companion object {
        const val URL: String = "/api/customers"
    }

    @BeforeEach
    fun setUp() {
        customerRepository.deleteAll()
    }

    @Test
    fun `should save a customer and return http 201 status`() {
        // given
        val customerDto = EntityBuilder.buildCustomerDto()
        val valueAsString = objectMapper.writeValueAsString(customerDto)

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(valueAsString))
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("First"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Last"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("86155985090"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("email@gmail.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("11222333"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Logo ali"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.income").value(BigDecimal.ZERO))
    }

    @Test
    fun `should not save a customer with same cpf and return http 409 status`() {
        // given
        customerRepository.save(EntityBuilder.buildCustomerDto().toEntity())
        val customerDto = EntityBuilder.buildCustomerDto()
        val valueAsString = objectMapper.writeValueAsString(customerDto)

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(valueAsString))
            .andExpect(MockMvcResultMatchers.status().isConflict)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Conflict"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(409))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class org.springframework.dao.DataIntegrityViolationException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
    }

    @Test
    fun `should not save a customer with empty firstName and return http 400 status`() {
        // given
        val customerDto = EntityBuilder.buildCustomerDto(firstName = "")
        val valueAsString = objectMapper.writeValueAsString(customerDto)

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
    fun `should find customer by id and return http 200 status`() {
        // given
        val customer = customerRepository.save(EntityBuilder.buildCustomerDto().toEntity())

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("$URL/${customer.id}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("First"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Last"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("86155985090"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("email@gmail.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("11222333"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Logo ali"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.income").value(BigDecimal.valueOf(0.0)))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should not find a customer with invalid id and return http 400 status`() {
        // given
        val invalidId = 1L

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("$URL/$invalidId")
            .contentType(MediaType.APPLICATION_JSON))
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
    fun `should delete a customer by id and return http 204 status`() {
        // given
        val customer = customerRepository.save(EntityBuilder.buildCustomerDto().toEntity())

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.delete("$URL/${customer.id}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNoContent)
            .andExpect(MockMvcResultMatchers.jsonPath("$").value("customer ${customer.email} successfully deleted"))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should not delete a customer with invalid id and return http 400 status`() {
        // given
        val invalidId = 1L

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.delete("$URL/$invalidId")
            .contentType(MediaType.APPLICATION_JSON))
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
    fun `should update a customer and return http 200 status`() {
        // given
        val customer = customerRepository.save(EntityBuilder.buildCustomerDto().toEntity())
        val customerUpdatedDto = EntityBuilder.buildCustomerUpdateDto()
        val valueAsString = objectMapper.writeValueAsString(customerUpdatedDto)

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.patch("$URL?customerId=${customer.id}")
            .contentType(MediaType.APPLICATION_JSON)
            .content(valueAsString))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("FirstUpdated"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("LastUpdated"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("86155985090"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("email@gmail.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("44555666"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Lugar algum"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.income").value(BigDecimal.valueOf(10000.0)))
    }

    @Test
    fun `should not update a customer with invalid id and return http 400 status`() {
        // given
        val invalidId = 1L
        val customerUpdatedDto = EntityBuilder.buildCustomerUpdateDto()
        val valueAsString = objectMapper.writeValueAsString(customerUpdatedDto)

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.patch("$URL?customerId=$invalidId")
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
}