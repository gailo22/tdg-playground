package com.example.jetpack.keycloaklogin.data.models

import com.example.jetpack.keycloaklogin.domain.models.Icon
import kotlinx.serialization.Serializable

@Serializable
data class IconDto(
    val url: String?
)

fun IconDto.toDomain(): Icon {
    return Icon(url = url)
}
