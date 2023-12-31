package moura1001.creditapplicationsystem.repository

import moura1001.creditapplicationsystem.domain.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository<Customer, Long> {
}