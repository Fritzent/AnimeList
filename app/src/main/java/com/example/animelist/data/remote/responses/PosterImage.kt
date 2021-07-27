package com.example.animelist.data.remote.responses

data class PosterImage(
    val large: String,
    val medium: String,
    val meta: MetaX,
    val original: String,
    val small: String,
    val tiny: String
)