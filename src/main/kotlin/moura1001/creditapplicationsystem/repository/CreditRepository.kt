package moura1001.creditapplicationsystem.repository

import moura1001.creditapplicationsystem.domain.Credit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface CreditRepository : JpaRepository<Credit, UUID> {
    fun findByCustomerIdAndCreditCode(customerId: Long, creditCode: UUID) : Optional<Credit>

    @Query(value = "SELECT * FROM credits WHERE customer_id = ?1", nativeQuery = true)
    fun findAllCredits(customerId: Long) : List<Credit>
}