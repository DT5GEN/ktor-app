package com.dt5gen

import com.dt5gen.entities.NotesEntity
import com.dt5gen.plugins.*
import com.dt5gen.services.UserService
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import org.ktorm.database.Database
import org.ktorm.dsl.insert

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

    database.useConnection { connection ->
        val statement = connection.createStatement()
        statement.executeUpdate("""
        CREATE TABLE IF NOT EXISTS note (
            id SERIAL PRIMARY KEY,
            note VARCHAR(255) NOT NULL
        );
    """.trimIndent())
        statement.close()
    }

    database.insert(NotesEntity){
        set(it.note, "Practice KtORM")
    }

    // Создание экземпляра сервиса
    val userService = UserService(database)

    // Вызов функции для получения всех пользователей
    val users = userService.fetchAllUsers()
    println(users)
    println(users)
    println(users)
    println(users)




    configureSecurity()
    configureRouting()
    contactUsModule()

}