package com.example.jetpack.keycloaklogin.domain.models

data class HomePage(
    val name: String,
    val shelves: List<Shelf> = arrayListOf()
)