package moura1001.creditapplicationsystem.dto

import moura1001.creditapplicationsystem.domain.Credit
import moura1001.creditapplicationsystem.domain.Status
import java.math.BigDecimal
import java.util.UUID

class CreditViewInfo(
    val creditCode: UUID,
    val creditValue: BigDecimal = BigDecimal.ZERO,
    val numberOfInstallments: Int = 0,
    val status: Status,
    val emailCustomer: String,
    val incomeCustomer: BigDecimal,
) {
    constructor(credit: Credit) : this(
        creditCode = credit.creditCode,
        creditValue = credit.creditValue,
        numberOfInstallments = credit.numberOfInstallments,
        status = credit.status,
        emailCustomer = credit.customer!!.email,
        incomeCustomer = credit.customer!!.income,
    )
}
