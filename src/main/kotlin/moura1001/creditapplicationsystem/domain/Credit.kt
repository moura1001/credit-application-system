package moura1001.creditapplicationsystem.domain

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "credits")
data class Credit(
    @Id val creditCode: UUID = UUID.randomUUID(),
    @Column(nullable = false) val creditValue: BigDecimal = BigDecimal.ZERO,
    @Column(nullable = false) val dayFirstInstallment: LocalDate = LocalDate.now().plusDays(30),
    @Column(nullable = false) val numberOfInstallments: Int = 0,
    @Column(nullable = false) @Enumerated val status: Status = Status.IN_PROGRESS,
    @ManyToOne(fetch = FetchType.EAGER) var customer: Customer? = null,
    @Column(name = "customer_id", nullable = false, insertable = false, updatable = false)
    var customerId: Long? = null,
)
