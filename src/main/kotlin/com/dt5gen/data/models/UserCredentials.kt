package com.dt5gen.data.models

import kotlinx.serialization.Serializable
import org.mindrot.jbcrypt.BCrypt

@Serializable
data class UserCredentials(
    val username: String,
    val password: String
){
    fun passwordHasher(): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    fun isValidCredentials(): Boolean {
        return username.length >= 4 && password.length >= 6
    }

}

