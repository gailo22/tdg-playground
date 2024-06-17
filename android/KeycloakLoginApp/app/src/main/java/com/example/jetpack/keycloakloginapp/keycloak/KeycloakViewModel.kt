package com.example.jetpack.keycloakloginapp.keycloak

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack.keycloaklogin.domain.models.HomePage
import com.example.jetpack.keycloaklogin.domain.models.Item
import com.example.jetpack.keycloaklogin.domain.models.KeycloakUser
import com.example.jetpack.keycloaklogin.domain.usecases.GetBridgeTokenUseCase
import com.example.jetpack.keycloaklogin.domain.usecases.GetHomePageUseCase
import com.example.jetpack.keycloaklogin.domain.usecases.GetItemsUseCase
import com.example.jetpack.keycloaklogin.domain.usecases.GetUserInfoUseCase
import com.example.jetpack.keycloaklogin.domain.usecases.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KeycloakViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getHomePageUseCase: GetHomePageUseCase,
    private val getItemsUseCase: GetItemsUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getBridgeTokenUseCase: GetBridgeTokenUseCase
): ViewModel() {

    init {
        getUser()
        getBridgeToken()
        getUserProfile()
    }

    private val _user: MutableState<KeycloakUser?> = mutableStateOf(null)
    val user: State<KeycloakUser?> = _user

    private val _homePage: MutableState<HomePage?> = mutableStateOf(null)
    val homePage: State<HomePage?> = _homePage

    private val _items: MutableState<List<Item>?> = mutableStateOf(null)
    val items: State<List<Item>?> = _items

    private val _userProfile: MutableState<String?> = mutableStateOf(null)
    val userProfile: State<String?> = _userProfile

    private val _bridgeToken: MutableState<String?> = mutableStateOf(null)
    val bridgeToken: State<String?> = _bridgeToken

    private fun getUser() {
        viewModelScope.launch {
            val user = getUserInfoUseCase.invoke()
            _user.value = user
        }
    }

    fun getHomePage() {
        viewModelScope.launch {
            val homePage = getHomePageUseCase.invoke()
            _homePage.value = homePage
        }
    }

    fun getItems() {
        viewModelScope.launch {
            val items = getItemsUseCase.invoke()
            _items.value = items
        }
    }

    fun getUserProfile() {
        viewModelScope.launch {
            val userProfile = getUserProfileUseCase.invoke()
            _userProfile.value = userProfile
        }
    }

    fun getBridgeToken() {
        viewModelScope.launch {
            val bridgeToken = getBridgeTokenUseCase.invoke()
            _bridgeToken.value = bridgeToken
        }
    }

    fun logout() {
        _user.value = null
    }
}