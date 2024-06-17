package com.example.jetpack.keycloakloginapp.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack.keycloaklogin.domain.models.HomePage
import com.example.jetpack.keycloaklogin.domain.models.KeycloakUser
import com.example.jetpack.keycloaklogin.domain.usecases.GetHomePageUseCase
import com.example.jetpack.keycloaklogin.domain.usecases.SaveKeycloakTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.PrimitiveIterator
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val saveKeycloakTokenUseCase: SaveKeycloakTokenUseCase
): ViewModel() {

    fun setToken(token: String) = saveKeycloakTokenUseCase.invoke(token)

}