package com.example.jetpack.keycloaklogin.data.models

import com.example.jetpack.keycloaklogin.domain.models.Icon
import com.example.jetpack.keycloaklogin.domain.models.Item
import kotlinx.serialization.Serializable

@Serializable
class ItemDto(
    val title: String,
    val setting: SettingDto,
    val thumb: ThumbDto
)
fun ItemDto.toDomain(): Item {
    return Item(
        title = title,
        setting = setting.toDomain(),
        thumb = thumb.toDomain()
    )
}
