package com.dt5gen.db

import org.ktorm.database.Database

object DatabaseConnection {
    val database = Database.connect(
        url = "jdbc:postgresql://localhost:5432/notes_db",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "my_@_password"
    )


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
    }
}