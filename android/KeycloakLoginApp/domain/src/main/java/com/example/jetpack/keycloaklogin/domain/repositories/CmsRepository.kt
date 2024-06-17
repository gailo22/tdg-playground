package com.example.jetpack.keycloaklogin.domain.repositories

import com.example.jetpack.keycloaklogin.domain.models.HomePage
import com.example.jetpack.keycloaklogin.domain.models.Item

interface CmsRepository {

    suspend fun getHomePage(): HomePage

    suspend fun getItems(): List<Item>
}