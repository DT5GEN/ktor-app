package com.dt5gen.plugins


import io.ktor.http.ContentDisposition
import io.ktor.http.HttpHeaders

import io.ktor.server.application.*
import io.ktor.server.request.receive
import io.ktor.server.request.uri
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import java.io.File

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

        get("/download/1") {
            val file = File("/Users/marin/IdeaProjects/ktor-app/files/logo BBTTR2.png")
            call.response.header(
                HttpHeaders.ContentDisposition,
                ContentDisposition.Attachment.withParameter(
                    ContentDisposition.Parameters.FileName, "downloadableImage.png"

                ).toString()
            )
            call.respondFile(file)


        }

        get("/download/2") {
            val file2 = File("/Users/marin/IdeaProjects/ktor-app/files/log_nin_car.webp")
            call.response.header(
                HttpHeaders.ContentDisposition,
                ContentDisposition.Inline.withParameter(
                    ContentDisposition.Parameters.FileName, "downloadableImage2.webp"

                ).toString()
            )
            call.respondFile(file2)
        }

        post("/login") {
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
