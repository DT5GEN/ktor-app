package com.dt5gen


import com.dt5gen.data.models.NotesEntity
import com.dt5gen.data.models.NotesEntity.note
import com.dt5gen.plugins.*
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


    install(ContentNegotiation) {
        json()
    }


    val database = Database.connect(
        url = "jdbc:postgresql://localhost:5432/notes_db",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "my_@_password"
    )

    val notes = database
        .from(NotesEntity)
        .select()
        .map { row ->  // Используем map для обработки каждой строки
            println("${row[NotesEntity.id]} : ${row[NotesEntity.note]}")
        }



    configureSecurity()
    configureRouting()
    contactUsModule()

}