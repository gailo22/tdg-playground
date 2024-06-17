package com.example.jetpack.keycloaklogin.domain.usecases

import com.example.jetpack.keycloaklogin.domain.repositories.KeycloakRepository
import javax.inject.Inject

class SaveKeycloakTokenUseCase @Inject constructor(private val repository: KeycloakRepository) {
    operator fun invoke(token: String) = repository.addKeycloakToken(token)
}