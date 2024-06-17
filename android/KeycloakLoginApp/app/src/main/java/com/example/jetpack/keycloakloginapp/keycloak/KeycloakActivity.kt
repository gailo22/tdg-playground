package com.example.jetpack.keycloakloginapp.keycloak

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.jetpack.keycloakloginapp.screens.MainScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class KeycloakActivity : ComponentActivity() {
    private val viewModel: KeycloakViewModel by viewModels()

    @Inject
    lateinit var qrCodeScanner: QrCodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                MainScreen(navController, viewModel, qrCodeScanner)
            }
        }
    }
}

