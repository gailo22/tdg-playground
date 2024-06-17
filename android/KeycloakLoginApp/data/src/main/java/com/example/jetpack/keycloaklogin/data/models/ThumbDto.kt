package com.example.jetpack.keycloaklogin.data.models

import com.example.jetpack.keycloaklogin.domain.models.Icon
import com.example.jetpack.keycloaklogin.domain.models.Thumb
import kotlinx.serialization.Serializable

@Serializable
data class ThumbDto(
    val url: String
)

fun ThumbDto.toDomain(): Thumb {
    return Thumb(url = url)
}
