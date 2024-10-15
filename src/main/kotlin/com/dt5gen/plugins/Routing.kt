package com.dt5gen.plugins

import com.dt5gen.routing.authenticationRoutes
import com.dt5gen.routing.notesRoutes
import io.ktor.http.ContentDisposition
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.receive
import io.ktor.server.request.uri
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import java.io.File

fun Application.configureRouting() {

    authenticationRoutes()

    routing {
        get("/") {

            println("URI: ${call.request.uri}")
            println("Headers: ${call.request.headers.names()}")
            println("User-Agent: ${call.request.headers["User-Agent"]}")
            println("Accept: ${call.request.headers["Accept"]}")
            println("Query-params: ${call.request.queryParameters.names()}")
            println("Name: ${call.request.queryParameters["name"]}")
            println("Email: ${call.request.queryParameters["email"]}")

            //call.respond("Hello fucking World!")
            val responseObject = UserResponse("Alex", "1r@ya.netu")
            call.respond(responseObject)
            call.respondText("Something went wrong!", status = HttpStatusCode.NotFound)
        }

//        get("/notes/{page}") {
//            call.respondText("You are on page: ${call.parameters["page"]}")
//        }


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

            get("/headers") {
                call.response.headers.append("server_name", "Ktor Test Server App")
                call.response.headers.append("Asian_girl", "I love them")
                call.respondText("HEADERS ATTACHED")

            }

            post("/login") {
                val userInfo = call.receive<UserInfo>()
                println(userInfo)
                call.respondText("You are logged in")
            }
        }
    }

    notesRoutes()

}
    @Serializable
    data class UserInfo(
        val name: String,
        val email: String,
        val password: String
    )

    @Serializable
    data class UserResponse(
        val name: String,
        val email: String
    )

