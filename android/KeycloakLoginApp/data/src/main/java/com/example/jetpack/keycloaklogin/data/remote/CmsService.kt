package com.example.jetpack.keycloaklogin.data.remote

import com.example.jetpack.keycloaklogin.data.models.DataWrapperDto
import com.example.jetpack.keycloaklogin.data.models.HomePageDto
import com.example.jetpack.keycloaklogin.data.models.ItemDto
import com.example.jetpack.keycloaklogin.data.models.KeycloakUserDto
import io.ktor.client.HttpClient
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.get
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.ANDROID
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import kotlinx.serialization.json.Json
import javax.inject.Inject

class CmsService @Inject constructor() {

    private val client = HttpClient {
//        val token = preferences.getKeycloakToken()
//        defaultRequest {
//            header("Authorization", "Bearer $token")
//        }

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

    suspend fun getHomePage(): HomePageDto {
        val dataWrapperDto: DataWrapperDto<HomePageDto> = client.get("http://192.168.1.40:1337/api/home?populate[0]=shelves.miniprograms.icon")

        return dataWrapperDto.data
    }

    suspend fun getItems(): List<ItemDto> {
        val dataWrapperDto: DataWrapperDto<List<ItemDto>> = client.get("https://truex-cms-admin-panel-dev.truex.cloud/api/items?populate[setting]=*&populate[thumb][fields][0]=url")

        return dataWrapperDto.data
    }
}