package com.dt5gen.services

import com.dt5gen.data.models.UserModel
import org.ktorm.database.Database
import org.ktorm.dsl.from
import org.ktorm.dsl.map
import org.ktorm.dsl.select

class UserService(private val database: Database) {

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
}

