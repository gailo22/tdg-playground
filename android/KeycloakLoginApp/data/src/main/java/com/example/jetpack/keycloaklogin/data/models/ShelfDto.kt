package com.example.jetpack.keycloaklogin.data.models

import com.example.jetpack.keycloaklogin.domain.models.HomePage
import com.example.jetpack.keycloaklogin.domain.models.MiniProgram
import com.example.jetpack.keycloaklogin.domain.models.Shelf
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShelfDto(
    val type: String,
    @SerialName("miniprograms")
    val miniPrograms : List<MiniProgramDto> = arrayListOf()
)

fun ShelfDto.toDomain(): Shelf {
    val list = miniPrograms.map {
        it.toDomain()
    }.toList()
    return Shelf(type = type, miniPrograms = list)
}
