package com.example.moneymate.model

data class Staff(
    val id: Int,
    val uname: String,
    val email: String,
    val password: String,
    val regDate: String
) {
    override fun toString(): String {
        return """id: $id
           UserName : $uname
           Email : $email
           Password : $password
           Registration Date: $regDate 
        """
    }
}