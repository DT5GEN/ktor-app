package com.dt5gen.routing

import com.dt5gen.data.models.NoteResponse
import com.dt5gen.data.models.UserCredentials
import com.dt5gen.data.models.UserLoginable
import com.dt5gen.data.models.UsersEntity
import com.dt5gen.db.DatabaseConnection
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import org.mindrot.jbcrypt.BCrypt

fun Application.authenticationRoutes() {

    val db = DatabaseConnection.database

    routing {
        post("/register") {
            val userCredentials = call.receive<UserCredentials>()

            if (!userCredentials.isValidCredentials()) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    NoteResponse(
                        success = false,
                        data = "Username should be greater than or equal 4, and password should be greater than or equal 6"
                    )
                )
                return@post
            }


            val username = userCredentials.username.lowercase()
            val password = userCredentials.passwordHasher()

            // Check if user already exists
            val user = db.from(UsersEntity)
                .select()
                .where { UsersEntity.username eq username }
                .map { it[UsersEntity.username] }
                .firstOrNull()

            if (user != null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    NoteResponse(success = false, data = "User already exists , please try a different username")
                )
                return@post
            }

            db.insert(UsersEntity) {
                set(it.username, username)
                set(it.password, password)
            }
            call.respond(
                HttpStatusCode.Created,
                NoteResponse(success = true, data = "User has been  successfully created")
            )
        }

        post("/login") {
            val userCredentials = call.receive<UserCredentials>()

            if (!userCredentials.isValidCredentials()) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    NoteResponse(
                        success = false,
                        data = "Username should be greater than or equal 4, and password should be greater than or equal 6"
                    )
                )
                return@post
            }


            val username = userCredentials.username.lowercase()
            val password = userCredentials.password
            //val password = userCredentials.passwordHasher()

            // Check if user exists
            val user = db.from(UsersEntity)
                .select()
                .where { UsersEntity.username eq username }
                .map {
                    val id = it[UsersEntity.id]
                    val username = it[UsersEntity.username]
                    val password = it[UsersEntity.password]
                    UserLoginable(id, username, password)
                }.firstOrNull()

            if (user == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    NoteResponse(success = false, data = "Invalid username or password")
                )
                return@post
            }

            val doesPasswordMach = BCrypt.checkpw(password, user.password)
            if (!doesPasswordMach) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    NoteResponse(success = false, data = "Invalid username or password")
                )
                return@post

            }

            call.respond(
                HttpStatusCode.OK,
                NoteResponse(success = true, data = "User has been  successfully logged in")
            )

        }
    }
}

//
