package com.dt5gen.data.models

import org.ktorm.schema.Table
import org.ktorm.schema.varchar

object User : Table<Nothing>("user") {
    val email = varchar("email").primaryKey()
    val name = varchar("name")
    val hashPassword = varchar("hashPassword")
}