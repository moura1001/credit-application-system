package moura1001.creditapplicationsystem.repository

import moura1001.creditapplicationsystem.domain.Credit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CreditRepository : JpaRepository<Credit, UUID> {
}