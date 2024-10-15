package com.dt5gen


import com.dt5gen.data.models.NotesEntity
import com.dt5gen.data.models.NotesEntity.note
import com.dt5gen.db.DatabaseConnection
import com.dt5gen.plugins.*
import com.dt5gen.services.SomethingService
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import org.ktorm.database.Database
import org.ktorm.dsl.from
import org.ktorm.dsl.map
import org.ktorm.dsl.select

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module(testing: Boolean = false) {

    DatabaseConnection.createUsersTable()
    install(ContentNegotiation) {
        json()
    }

    configureSecurity()
    configureRouting()
    contactUsModule()

}