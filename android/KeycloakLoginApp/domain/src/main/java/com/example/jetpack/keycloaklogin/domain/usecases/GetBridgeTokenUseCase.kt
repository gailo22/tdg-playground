package com.example.jetpack.keycloaklogin.domain.usecases

import com.example.jetpack.keycloaklogin.domain.models.KeycloakUser
import com.example.jetpack.keycloaklogin.domain.repositories.AuthRepository
import com.example.jetpack.keycloaklogin.domain.repositories.KeycloakRepository
import javax.inject.Inject


class GetBridgeTokenUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): String {
       return authRepository.login()
    }
}