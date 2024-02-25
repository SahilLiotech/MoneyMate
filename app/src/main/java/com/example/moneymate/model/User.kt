package com.example.moneymate.model

data class User(
    val id: Int? = null,
    val uname: String? = null,
    val email: String? = null,
    val password: String? = null,
    val regDate: String? = null
) {
    override fun toString(): String {
        return """
            User {
                id: ${this.id},
                uname: ${this.uname},
                email: ${this.email},
                password: ${this.password},
                regdate: ${this.regDate}
            }
        """
    }
}