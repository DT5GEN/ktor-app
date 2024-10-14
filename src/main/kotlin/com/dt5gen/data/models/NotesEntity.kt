package com.dt5gen.data.models

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object NotesEntity : Table<Nothing>("note") {
    val id = int("id").primaryKey()
    val note = varchar("note")
}

