package com.dt5gen.data.models

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object UsersEntity : Table<Nothing>("users") {
    val id = int("id").primaryKey()
    val username = varchar("username")
    val password = varchar("password")
}
