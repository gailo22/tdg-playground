package com.example.jetpack.keycloakloginapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.jetpack.keycloakloginapp.keycloak.KeycloakViewModel

@Composable
fun ProfileScreen(viewModel: KeycloakViewModel) {
    KeycloakProfile(viewModel)
}

@Composable
fun CenterText(text: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = text, fontSize = 32.sp)
    }
}

@Composable
private fun KeycloakProfile(viewModel: KeycloakViewModel) {
    val profile by remember { viewModel.user }
    profile?.let {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Title(
                text = "You're logged in!"
            )
            UserInfoRow(
                label = "Name",
                value = it.name,
            )
            UserInfoRow(
                label = "Nick Name",
                value = it.nickname,
            )
            UserPicture(
                url = "https://images.ctfassets.net/23aumh6u8s0i/5hHkO5DxWMPxDjc2QZLXYf/403128092dedc8eb3395314b1d3545ad/icon-user.png",
                description = it.name,
            )
        }
    }
}


@Composable
fun Title(
    text: String,
) {
    Text(
        text = text,
        style = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
        )
    )
}

@Composable
fun UserInfoRow(
    label: String,
    value: String,
) {
    Row {
        Text(
            text = label,
            style = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        )
        Spacer(
            modifier = Modifier.width(10.dp),
        )
        Text(
            text = value,
            style = TextStyle(
                fontFamily = FontFamily.Default,
                fontSize = 20.sp,
            )
        )
    }
}

@Composable
fun UserPicture(
    url: String,
    description: String,
) {
    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = rememberAsyncImagePainter(url),
            contentDescription = description,
            modifier = Modifier.fillMaxSize(0.5f),
        )
    }
}

@Composable
fun LogButton(
    text: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = { onClick() },
            modifier = Modifier
                .width(200.dp)
                .height(50.dp),
        ) {
            Text(
                text = text,
                fontSize = 20.sp,
            )
        }
    }
}
