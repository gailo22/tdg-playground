package com.example.jetpack.keycloaklogin.domain.usecases

import com.example.jetpack.keycloaklogin.domain.models.KeycloakUser
import com.example.jetpack.keycloaklogin.domain.repositories.KeycloakRepository
import javax.inject.Inject


class GetUserInfoUseCase @Inject constructor(private val keycloakRepository: KeycloakRepository) {
    suspend operator fun invoke(): KeycloakUser {
       return keycloakRepository.getUserInfo()
    }
}