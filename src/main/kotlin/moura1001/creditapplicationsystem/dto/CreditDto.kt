package moura1001.creditapplicationsystem.dto

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import moura1001.creditapplicationsystem.domain.Credit
import java.math.BigDecimal
import java.time.LocalDate

data class CreditDto(
    @field:NotNull @field:Positive val creditValue: BigDecimal = BigDecimal.ZERO,
    @field:NotNull @field:Future val dayFirstInstallment: LocalDate = LocalDate.now().plusDays(30),
    @field:NotNull @field:Positive val numberOfInstallments: Int = 0,
    @field:NotNull @field:Positive val customerId: Long = -1
) {
    fun toEntity(): Credit {
        // validations

        return Credit(
            creditValue = this.creditValue,
            dayFirstInstallment = this.dayFirstInstallment,
            numberOfInstallments = this.numberOfInstallments,
            customerId = this.customerId
        )
    }
}
