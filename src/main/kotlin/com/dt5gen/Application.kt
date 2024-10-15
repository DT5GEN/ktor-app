package com.dt5gen

import com.dt5gen.db.DatabaseConnection
import com.dt5gen.plugins.*
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {

    DatabaseConnection.createUsersTable()
    install(ContentNegotiation) {
        json()
    }
    install(Authentication){
        jwt{

        }
    }

    configureSecurity()
    configureRouting()
    contactUsModule()

}