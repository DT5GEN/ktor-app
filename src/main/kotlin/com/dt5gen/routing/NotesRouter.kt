package com.dt5gen.routing

import com.dt5gen.data.models.Note
import com.dt5gen.data.models.NoteRequest
import com.dt5gen.data.models.NoteResponse
import com.dt5gen.data.models.NotesEntity
import com.dt5gen.db.DatabaseConnection
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select

fun Application.notesRoutes() {

    val db = DatabaseConnection.database

    routing {
        get("/notes") {
            val notes = db.from(NotesEntity)
                .select()
                .map {
                    val id = it[NotesEntity.id]
                    val note = it[NotesEntity.note]
                    Note(id ?: -1, note ?: "")
                }
            call.respond(notes)
        }

        post("/notes") {
            val request = call.receive<NoteRequest>()
            val result = db.insert(NotesEntity) {
                set(it.note, request.note)
            }
            if (result == 1){
                // Sent successfully response to the client
                call.respond(HttpStatusCode.OK, NoteResponse(
                    success = true,
                    data = "Values has been successfully inserted!"
                ))
            } else {
                // Sent failure response to the client
                call.respond(HttpStatusCode.BadRequest, NoteResponse(
                    success = false,
                    data = "Failed to insert ${request.note}!"
                ))
            }
        }
    }
}