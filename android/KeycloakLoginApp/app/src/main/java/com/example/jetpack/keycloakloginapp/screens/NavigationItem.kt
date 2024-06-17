package com.example.jetpack.keycloakloginapp.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.jetpack.keycloakloginapp.screens.QR

sealed class NavigationItem(var route: String, val icon: ImageVector?, var title: String) {
    data object Home : NavigationItem("Home", Icons.Rounded.Home, "Home")
    data object Scan : NavigationItem("Scan", Icons.Rounded.QR, "Scan")
    data object Profile : NavigationItem("Profile", Icons.Rounded.AccountBox, "Profile")
}
