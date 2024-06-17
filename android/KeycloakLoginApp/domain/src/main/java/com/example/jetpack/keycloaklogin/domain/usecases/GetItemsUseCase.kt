package com.example.jetpack.keycloaklogin.domain.usecases

import com.example.jetpack.keycloaklogin.domain.models.HomePage
import com.example.jetpack.keycloaklogin.domain.models.Item
import com.example.jetpack.keycloaklogin.domain.repositories.CmsRepository
import javax.inject.Inject

class GetItemsUseCase @Inject constructor(
    private val cmsRepository: CmsRepository
) {
    suspend operator fun invoke(): List<Item> {
        return cmsRepository.getItems()
    }
}