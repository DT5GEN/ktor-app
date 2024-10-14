package com.dt5gen.db

import org.ktorm.database.Database

object DatabaseConnection {
    val database = Database.connect(
        url = "jdbc:postgresql://localhost:5432/notes_db",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "my_@_password"
    )
}