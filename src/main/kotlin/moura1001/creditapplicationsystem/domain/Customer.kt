package moura1001.creditapplicationsystem.domain

data class Customer (
    var id: Long? = null,
    var firstName: String = "",
    var lastName: String = "",
    val cpf: String,
    var email: String = "",
    var password: String = "",
    var address: Address = Address(),
    val credits: List<Credit> = mutableListOf(),
)