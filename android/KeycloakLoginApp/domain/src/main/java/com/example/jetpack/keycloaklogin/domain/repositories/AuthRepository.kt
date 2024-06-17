package com.example.jetpack.keycloaklogin.domain.repositories

import com.example.jetpack.keycloaklogin.domain.models.HomePage
import com.example.jetpack.keycloaklogin.domain.models.Item
import java.security.SecureRandom

interface AuthRepository {

    suspend fun login(): String

    suspend fun getUserProfile(): String
}