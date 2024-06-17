package com.example.jetpack.keycloaklogin.data.remote

import com.example.jetpack.keycloaklogin.data.local.Preferences
import com.example.jetpack.keycloaklogin.data.models.KeycloakUserDto
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json as serializer
import javax.inject.Inject

class KeycloakService @Inject constructor(private val preferences: Preferences){

    private val client = HttpClient {
        val token = preferences.getKeycloakToken()
        defaultRequest {
            header("Authorization", "Bearer $token")
        }

        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.ALL
        }

        install(JsonFeature) {
            serializer = KotlinxSerializer(serializer {
                isLenient = true
                prettyPrint = true
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getProfile(): KeycloakUserDto {
        return client.get("https://keycloak.montree.me/realms/my-auth/protocol/openid-connect/userinfo")
    }
}