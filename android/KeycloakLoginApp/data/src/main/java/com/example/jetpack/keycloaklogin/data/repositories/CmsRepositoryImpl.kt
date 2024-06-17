package com.example.jetpack.keycloaklogin.data.repositories

import com.example.jetpack.keycloaklogin.data.models.toDomain
import com.example.jetpack.keycloaklogin.data.remote.CmsService
import com.example.jetpack.keycloaklogin.domain.models.HomePage
import com.example.jetpack.keycloaklogin.domain.models.Item
import com.example.jetpack.keycloaklogin.domain.repositories.CmsRepository
import javax.inject.Inject

class CmsRepositoryImpl @Inject constructor(
    private val cmsService: CmsService
): CmsRepository {
    override suspend fun getHomePage(): HomePage {
        return cmsService.getHomePage().toDomain()
    }

    override suspend fun getItems(): List<Item> {
        return cmsService.getItems().map {
            it.toDomain()
        }
    }
}