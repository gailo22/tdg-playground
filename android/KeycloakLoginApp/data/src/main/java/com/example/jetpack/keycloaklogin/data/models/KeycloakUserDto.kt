package com.example.jetpack.keycloaklogin.data.models

import androidx.room.Entity
import com.example.jetpack.keycloaklogin.domain.models.KeycloakUser
import kotlinx.serialization.Serializable

@Serializable
@Entity(primaryKeys = ["sub"])
data class KeycloakUserDto(
    val sub: String,
    val name: String,
    val preferred_username: String,
    val given_name: String,
    val family_name: String,
)

//        {
//            "sub": "5ebce60a-be32-4b29-bf78-eccb330975be",
//            "email_verified": false,
//            "name": "John Wick",
//            "preferred_username": "john",
//            "given_name": "John",
//            "family_name": "Wick"
//        }

fun KeycloakUserDto.toDomain(): KeycloakUser {
    return KeycloakUser(
        name = name,
        nickname = preferred_username,
        firstname = given_name,
        lastname = family_name,
    )
}