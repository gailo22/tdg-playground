package com.example.jetpack.keycloaklogin.data.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseService {

    @Provides
    @Singleton
    fun provideDB(@ApplicationContext context: Context): KeycloakLoginDatabase {
        return Room.databaseBuilder(context, KeycloakLoginDatabase::class.java, KeycloakLoginDatabase.DB_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

}