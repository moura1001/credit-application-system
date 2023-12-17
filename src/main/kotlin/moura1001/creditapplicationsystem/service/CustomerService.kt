package moura1001.creditapplicationsystem.service

import moura1001.creditapplicationsystem.domain.Customer
import moura1001.creditapplicationsystem.repository.CustomerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CustomerService(
    private val customerRepository: CustomerRepository
) {
    @Transactional(rollbackFor = [RuntimeException::class, Exception::class])
    fun save(customer: Customer): Customer {
        return this.customerRepository.save(customer)
    }

    fun findById(id: Long): Customer {
        return this.customerRepository.findById(id).orElseThrow{
            throw RuntimeException("customer $id not found")
        }
    }

    @Transactional(rollbackFor = [RuntimeException::class, Exception::class])
    fun delete(id: Long) {
        this.customerRepository.deleteById(id)
    }
}