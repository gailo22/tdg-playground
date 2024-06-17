package com.example.jetpack.keycloaklogin.data.models

import com.example.jetpack.keycloaklogin.domain.models.Icon
import com.example.jetpack.keycloaklogin.domain.models.Setting
import kotlinx.serialization.Serializable

@Serializable
data class SettingDto(
    val navigateType: String,
    val miniAppID: String?,
    val navigate: String
)

fun SettingDto.toDomain(): Setting {
    return Setting(
        navigateType = navigateType,
        miniAppID = miniAppID,
        navigate = navigate
    )

}
