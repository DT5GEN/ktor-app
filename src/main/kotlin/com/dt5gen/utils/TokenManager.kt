package com.dt5gen.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.dt5gen.data.models.User
import com.dt5gen.data.models.UserLoginable
import io.ktor.server.config.HoconApplicationConfig

class TokenManager(val config: HoconApplicationConfig) {

    fun generateJWToken(user: UserLoginable) : String {
        val audience = config.property("audience").getString()
        val secret = config.property("secret").getString()
        val issuer = config.property("issuer").getString()
        val expirationDate = System.currentTimeMillis() + 60000;

        val token = JWT.create()
            .withIssuer(audience)
            .withIssuer(issuer)
            .withClaim("username", user.username)
            .withClaim("userId", user.id)
            .sign(Algorithm.HMAC256(secret))
        return token
    }
}