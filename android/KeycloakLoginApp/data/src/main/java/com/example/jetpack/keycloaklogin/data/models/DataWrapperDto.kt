package com.example.jetpack.keycloaklogin.data.models

import kotlinx.serialization.Serializable

@Serializable
data class DataWrapperDto<T>(
    val data: T,
)