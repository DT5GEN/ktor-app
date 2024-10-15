package com.dt5gen.routing

import com.dt5gen.data.models.UserCredentials
import com.dt5gen.data.models.UsersEntity
import com.dt5gen.db.DatabaseConnection
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.response.respondText
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.ktorm.dsl.insert

fun Application.authenticationRoutes() {

    val db = DatabaseConnection.database

    routing {
        post("/register") {
            val userCredentials = call.receive<UserCredentials>()
            val username = userCredentials.username.toLowerCase()
            val password = userCredentials.password

            db.insert(UsersEntity){
                set(it.username, username)
                set(it.password, password)
            }
            call.respondText("Values insert successful", status = HttpStatusCode.OK)
        }
    }
}