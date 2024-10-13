package com.dt5gen.plugins

import io.ktor.server.application.*
import io.ktor.server.request.receive
import io.ktor.server.request.uri
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Application.configureRouting() {
    routing {
        get("/") {

            println("URI: ${call.request.uri}")
            println("Headers: ${call.request.headers.names()}")
            println("User-Agent: ${call.request.headers["User-Agent"]}")
            println("Accept: ${call.request.headers["Accept"]}")
            println("Query-params: ${call.request.queryParameters.names()}")
            println("Name: ${call.request.queryParameters["name"]}")
            println("Email: ${call.request.queryParameters["email"]}")

            call.respond("Hello fucking World!")
        }

        get("/notes/{page}") {
            call.respondText("You are on page: ${call.parameters["page"]}")
        }

        post("/login"){
            val userInfo = call.receive<UserInfo>()
            println(userInfo)
            call.respondText("You are logged in")
        }
    }
}

@Serializable
data class UserInfo(
    val name: String,
    val email: String,
    val password: String
)
