package com.dt5gen.data.models

import kotlinx.serialization.Serializable

@Serializable
data class NoteRequest(
    val note: String
)
