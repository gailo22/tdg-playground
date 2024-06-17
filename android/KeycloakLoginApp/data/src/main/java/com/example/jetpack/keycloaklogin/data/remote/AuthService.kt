package com.example.jetpack.keycloaklogin.data.remote

import com.example.jetpack.keycloaklogin.data.local.Preferences
import io.ktor.client.HttpClient
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.ANDROID
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import javax.inject.Inject

class AuthService @Inject constructor(private val preferences: Preferences) {

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
            serializer = KotlinxSerializer(Json {
                isLenient = true
                prettyPrint = true
                ignoreUnknownKeys = true
            })
        }

    }

    suspend fun login(): String {
        val token = preferences.getKeycloakToken()

        val response: String = client.post("http://192.168.1.38:1337/api/tx-auth/login") {
            contentType(ContentType.Application.Json)
            body = LoginRequest(token)
        }
        println("Login......")
        println(response)

        return response
    }

    suspend fun getProfile(): String {
        val response: String = client.get("http://192.168.1.38:1337/api/tx-auth/user-profile")

        println("getProfile......")
        println(response)
        return response
    }
}

@Serializable
data class LoginRequest(
    val subjectToken: String?
)
