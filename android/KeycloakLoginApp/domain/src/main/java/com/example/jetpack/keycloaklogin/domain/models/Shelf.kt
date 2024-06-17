package com.example.jetpack.keycloaklogin.domain.models

data class Shelf(
    val type: String,
    val miniPrograms : List<MiniProgram> = arrayListOf()
)
