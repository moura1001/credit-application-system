package moura1001.creditapplicationsystem.dto

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import moura1001.creditapplicationsystem.domain.Credit
import moura1001.creditapplicationsystem.exception.BusinessException
import java.math.BigDecimal
import java.time.LocalDate

data class CreditDto(
    @field:NotNull @field:Positive val creditValue: BigDecimal = BigDecimal.ZERO,
    @field:NotNull @field:Future val dayFirstInstallment: LocalDate = LocalDate.now().plusDays(30),
    @field:NotNull @field:Positive @field:Max(value = 48) val numberOfInstallments: Int = 0,
    @field:NotNull @field:Positive val customerId: Long = -1
) {
    fun toEntity(): Credit {
        // validations
        if (dayFirstInstallment.isAfter(LocalDate.now().plusMonths(3)))
            throw BusinessException("entrada $dayFirstInstallment é inválida. Data da primeira parcela deve ser no máximo 3 meses após o dia atual.")

        return Credit(
            creditValue = this.creditValue,
            dayFirstInstallment = this.dayFirstInstallment,
            numberOfInstallments = this.numberOfInstallments,
            customerId = this.customerId
        )
    }
}
