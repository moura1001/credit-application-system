package moura1001.creditapplicationsystem.dto

import moura1001.creditapplicationsystem.domain.Credit
import java.math.BigDecimal
import java.time.LocalDate

data class CreditDto(
    val creditValue: BigDecimal = BigDecimal.ZERO,
    val dayFirstInstallment: LocalDate = LocalDate.now().plusDays(30),
    val numberOfInstallments: Int = 0,
    val customerId: Long = -1
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
