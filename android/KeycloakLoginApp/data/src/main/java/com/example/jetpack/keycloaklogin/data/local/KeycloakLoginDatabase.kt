package com.example.jetpack.keycloaklogin.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetpack.keycloaklogin.data.models.KeycloakUserDto

//@TypeConverters(Converters::class)
@Database(entities = [KeycloakUserDto::class], version = 1)
abstract class KeycloakLoginDatabase: RoomDatabase() {

    companion object {
        const val DB_NAME = "keycloak_login_database"
    }
}