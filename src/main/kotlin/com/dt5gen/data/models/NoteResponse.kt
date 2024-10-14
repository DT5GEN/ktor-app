package com.dt5gen.data.models

@kotlinx.serialization.Serializable
data class NoteResponse<T>(
    val data: T,
    val success: Boolean
)
