package moura1001.creditapplicationsystem.controller

import jakarta.validation.Valid
import moura1001.creditapplicationsystem.dto.CreditViewList
import moura1001.creditapplicationsystem.dto.CreditDto
import moura1001.creditapplicationsystem.dto.CreditViewInfo
import moura1001.creditapplicationsystem.service.CreditService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.UUID
import java.util.stream.Collectors

@RestController
@RequestMapping("/api/credits")
class CreditController(
    private val creditService: CreditService
) {
    @PostMapping
    fun saveCustomer(@RequestBody @Valid creditDto: CreditDto): ResponseEntity<String> {
        val savedCredit = this.creditService.save(creditDto.toEntity())
        return ResponseEntity.created(
            URI.create("/api/credits/${savedCredit.creditCode}?customerId=${savedCredit.customer!!.id}")
        ).body("credit ${savedCredit.creditCode} for ${savedCredit.customer!!.email} successfully saved")
    }

    @GetMapping
    fun findAllByCustomerId(@RequestParam(value = "customerId") customerId: Long): ResponseEntity<List<CreditViewList>> {
        val credits = this.creditService.findAllByCustomer(customerId).stream()
            .map { credit -> CreditViewList(credit) }.collect(Collectors.toList())
        return ResponseEntity.ok(credits)
    }

    @GetMapping("/{creditCode}")
    fun findByCreditCode(
        @RequestParam(value = "customerId") customerId: Long,
        @PathVariable creditCode: UUID
    ): ResponseEntity<CreditViewInfo> {
        val credit = this.creditService.findByCreditCode(customerId, creditCode)
        return ResponseEntity.ok(CreditViewInfo(credit))
    }
}