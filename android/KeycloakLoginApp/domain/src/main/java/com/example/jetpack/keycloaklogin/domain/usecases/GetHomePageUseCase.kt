package com.example.jetpack.keycloaklogin.domain.usecases

import com.example.jetpack.keycloaklogin.domain.models.HomePage
import com.example.jetpack.keycloaklogin.domain.repositories.CmsRepository
import javax.inject.Inject

class GetHomePageUseCase @Inject constructor(
    private val cmsRepository: CmsRepository
) {
    suspend operator fun invoke(): HomePage {
        return cmsRepository.getHomePage()
    }
}