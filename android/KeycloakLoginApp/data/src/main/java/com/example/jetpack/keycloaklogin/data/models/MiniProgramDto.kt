package com.example.jetpack.keycloaklogin.data.models

import com.example.jetpack.keycloaklogin.domain.models.MiniProgram
import kotlinx.serialization.Serializable

@Serializable
data class MiniProgramDto(
    val id: String,
    val miniId: String?,
    val name: String,
    val description: String,
    val icon: IconDto?,
)

fun MiniProgramDto.toDomain(): MiniProgram {
    return MiniProgram(
        name = name,
        miniId = miniId,
        description = description,
        icon = icon?.toDomain()
    )
}
