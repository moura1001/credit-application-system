package moura1001.creditapplicationsystem.exception

data class BusinessException(override val message: String?) : RuntimeException(message)
