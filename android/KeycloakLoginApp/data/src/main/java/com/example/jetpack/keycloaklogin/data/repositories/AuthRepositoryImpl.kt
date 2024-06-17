package com.example.jetpack.keycloaklogin.data.repositories

import com.example.jetpack.keycloaklogin.data.models.toDomain
import com.example.jetpack.keycloaklogin.data.remote.AuthService
import com.example.jetpack.keycloaklogin.data.remote.CmsService
import com.example.jetpack.keycloaklogin.domain.models.HomePage
import com.example.jetpack.keycloaklogin.domain.models.Item
import com.example.jetpack.keycloaklogin.domain.repositories.AuthRepository
import com.example.jetpack.keycloaklogin.domain.repositories.CmsRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
): AuthRepository {
    override suspend fun login(): String {
        return authService.login()
    }

    override suspend fun getUserProfile(): String {
        return authService.getProfile()
    }
}