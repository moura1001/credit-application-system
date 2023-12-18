package moura1001.creditapplicationsystem.service

import moura1001.creditapplicationsystem.domain.Credit
import moura1001.creditapplicationsystem.exception.BusinessException
import moura1001.creditapplicationsystem.repository.CreditRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class CreditService(
    private val creditRepository: CreditRepository,
    private val customerRepository: CustomerService
) {
    @Transactional(rollbackFor = [RuntimeException::class, Exception::class])
    fun save(credit: Credit): Credit {
        credit.apply {
            customer = customerRepository.findById(credit.customerId!!)
        }
        return this.creditRepository.save(credit)
    }

    fun findAllByCustomer(customerId: Long): List<Credit> {
        return this.creditRepository.findAllCredits(customerId)
    }

    fun findByCreditCode(customerId: Long, creditCode: UUID): Credit {
        return this.creditRepository.findByCustomerIdAndCreditCode(customerId, creditCode).orElseThrow{
            throw BusinessException("credit code $creditCode not found for customer $customerId")
        }
    }
}