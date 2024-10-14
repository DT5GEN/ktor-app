package com.dt5gen.routing

import com.dt5gen.data.models.Note
import com.dt5gen.data.models.NotesEntity
import com.dt5gen.db.DatabaseConnection
import io.ktor.server.application.Application
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.ktorm.dsl.from
import org.ktorm.dsl.map
import org.ktorm.dsl.select

fun Application.notesRoutes() {

    val db = DatabaseConnection.database

        routing {
        get("/notes"){
            val notes = db.from(NotesEntity)
                .select()
                .map {
                    val id = it[NotesEntity.id]
                    val note = it[NotesEntity.note]
                    Note(id ?: -1, note ?: "")
                }
                    call.respond(notes)
        }
    }
}