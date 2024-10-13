package com.dt5gen.plugins

import io.ktor.http.ContentType
import io.ktor.server.application.Application
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.contactUsModule() {
    routing {
        get("/contact_us") {
            call.respondText("contact us gh/@DT5GEN", ContentType.Text.Plain)
        }

        get("/about_us") {
            call.respondText("simple project crew", ContentType.Text.Plain)
        }
    }
}