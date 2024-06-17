package com.example.jetpack.keycloaklogin.data.repositories

import com.example.jetpack.keycloaklogin.domain.repositories.AuthRepository
import com.example.jetpack.keycloaklogin.domain.repositories.CmsRepository
import com.example.jetpack.keycloaklogin.domain.repositories.KeycloakRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class Bindings {

    @Binds
    abstract fun bindKeycloakRepository(impl: KeycloakRepositoryImpl): KeycloakRepository

    @Binds
    abstract fun bindCmsRepository(impl: CmsRepositoryImpl): CmsRepository

    @Binds
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

}