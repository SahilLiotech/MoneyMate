package com.example.moneymate.model

data class User(
    val id: Int,
    val uname: String,
    val email: String,
    val password: String,
    val regDate: String
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