package com.example.jetpack.keycloakloginapp.screens

import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.compose.rememberAsyncImagePainter
import com.example.jetpack.keycloakloginapp.keycloak.KeycloakActivity
import com.example.jetpack.keycloakloginapp.keycloak.KeycloakViewModel
import com.example.jetpack.keycloakloginapp.keycloak.QrCodeScanner
import com.example.jetpack.keycloakloginapp.ui.theme.lightestGray
import com.tencent.tmf.mini.api.TmfMiniSDK
import com.tencent.tmf.mini.api.bean.MiniApp
import com.tencent.tmf.mini.api.bean.MiniCode
import com.tencent.tmf.mini.api.bean.MiniScene
import com.tencent.tmf.mini.api.bean.MiniStartOptions
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: KeycloakViewModel,
    qrCodeScanner: QrCodeScanner
) {
    Scaffold(
        topBar = {
            CenterAligned()
        },
        bottomBar = {
            BottomAppBar(modifier = Modifier) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(
                PaddingValues(
                    0.dp,
                    0.dp,
                    0.dp,
                    innerPadding.calculateBottomPadding()
                )
            )
        ) {
            Navigations(navController, viewModel, qrCodeScanner)
        }
    }
}

@Composable
fun CenterAligned(){
    CenterAlignedAppBar()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterAlignedAppBar() {
    TopAppBar(
        title = {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("My App")
            }
        }
    )
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Scan,
        NavigationItem.Profile,
    )
    var selectedItem by remember { mutableStateOf(0) }
    var currentRoute by remember { mutableStateOf(NavigationItem.Home.route) }

    items.forEachIndexed { index, navigationItem ->
        if (navigationItem.route == currentRoute) {
            selectedItem = index
        }
    }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                alwaysShowLabel = true,
                icon = { Icon(item.icon!!, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    currentRoute = item.route
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun Navigations(
    navController: NavHostController,
    viewModel: KeycloakViewModel,
    qrCodeScanner: QrCodeScanner
) {
    NavHost(navController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) {
            HomeScreen(viewModel)
        }
        composable(NavigationItem.Scan.route) {
            ScanScreen(qrCodeScanner)
        }
        composable(NavigationItem.Profile.route) {
            ProfileScreen(viewModel)
        }
    }
}