package moura1001.creditapplicationsystem.dto

import moura1001.creditapplicationsystem.domain.Credit
import java.math.BigDecimal
import java.util.UUID

class CreditViewList(
    val creditCode: UUID,
    val creditValue: BigDecimal = BigDecimal.ZERO,
    val numberOfInstallments: Int = 0
) {
    constructor(credit: Credit) : this(
        creditCode = credit.creditCode,
        creditValue = credit.creditValue,
        numberOfInstallments = credit.numberOfInstallments
    )
}
