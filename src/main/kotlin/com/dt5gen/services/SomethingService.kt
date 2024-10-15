package com.dt5gen.services

import com.dt5gen.data.models.NotesModel
import com.dt5gen.data.models.UserModel
import org.ktorm.database.Database
import org.ktorm.dsl.from
import org.ktorm.dsl.map
import org.ktorm.dsl.select

class SomethingService(private val database: Database) {

    // Function for create new table

    fun createUsersTable() {
        database.useConnection { connection ->
            val statement = connection.createStatement()
            statement.executeUpdate(
                """
                CREATE TABLE IF NOT EXISTS users (
                    id SERIAL PRIMARY KEY,
                    username VARCHAR(100) NOT NULL,
                    password VARCHAR(500) NOT NULL
                );
            """.trimIndent()
            )
            statement.close()
        }

        // Функция для получения всех пользователей из таблицы
        fun fetchAllUsers(): List<UserModel> {
            return database
                .from(com.dt5gen.data.models.User)  // Обращаемся к таблице User
                .select()
                .map { row ->
                    UserModel(  // Создаём экземпляр UserModel для каждой строки
                        email = row[com.dt5gen.data.models.User.email] ?: "",
                        name = row[com.dt5gen.data.models.User.name] ?: "",
                        hashPassword = row[com.dt5gen.data.models.User.hashPassword] ?: ""
                    )
                }
        }

        // Функция для получения всех notes из таблицы
        fun fetchAllNotes(): List<NotesModel> {
            return database
                .from(com.dt5gen.data.models.NotesEntity)  // Обращаемся к таблице User
                .select()
                .map { row ->
                    NotesModel(
                        id = row[com.dt5gen.data.models.NotesEntity.id] ?: "",
                        note = row[com.dt5gen.data.models.NotesEntity.note] ?: ""
                    )
                }
        }

    }
}

