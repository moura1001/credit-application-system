package moura1001.creditapplicationsystem.controller

import jakarta.validation.Valid
import moura1001.creditapplicationsystem.dto.CustomerDto
import moura1001.creditapplicationsystem.dto.CustomerUpdateDto
import moura1001.creditapplicationsystem.dto.CustomerView
import moura1001.creditapplicationsystem.service.CustomerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/customers")
class CustomerController(
    private val customerService: CustomerService
) {
    @PostMapping
    fun saveCustomer(@RequestBody @Valid customerDto: CustomerDto): ResponseEntity<String> {
        val savedCustomer = this.customerService.save(customerDto.toEntity())
        return ResponseEntity.created(
            URI.create("/api/customers/${savedCustomer.id}")
        ).body("customer ${savedCustomer.email} successfully saved")
    }

    @GetMapping("/{id}")
    fun findCustomerById(@PathVariable id: Long): ResponseEntity<CustomerView> {
        val customer = this.customerService.findById(id)
        return ResponseEntity.ok(CustomerView(customer))
    }

    @DeleteMapping("/{id}")
    fun deleteCustomer(@PathVariable id: Long): ResponseEntity<String> {
        val customer = this.customerService.findById(id)
        this.customerService.delete(id)
        return ResponseEntity.ok("customer ${customer.email} successfully deleted")
    }

    @PatchMapping
    fun updateCustomer(
        @RequestParam(value = "customerId") id: Long,
        @RequestBody @Valid customerDto: CustomerUpdateDto
    ): ResponseEntity<CustomerView> {
        val customer = this.customerService.findById(id)
        val updatedCustomer = this.customerService.save(customerDto.toEntity(customer))
        return ResponseEntity.ok(CustomerView(updatedCustomer))
    }
}