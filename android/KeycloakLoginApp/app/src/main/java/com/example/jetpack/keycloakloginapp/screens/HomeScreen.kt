package com.example.jetpack.keycloakloginapp.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.jetpack.keycloaklogin.domain.models.Icon
import com.example.jetpack.keycloaklogin.domain.models.Item
import com.example.jetpack.keycloaklogin.domain.models.MiniProgram
import com.example.jetpack.keycloaklogin.domain.models.Shelf
import com.example.jetpack.keycloakloginapp.R
import com.example.jetpack.keycloakloginapp.keycloak.KeycloakViewModel
import okhttp3.internal.wait

@Composable
fun HomeScreen(viewModel: KeycloakViewModel) {
    //KeycloakProfile(viewModel)

    MiniProgramHome(viewModel)
}

@Composable
fun MiniProgramHome(viewModel: KeycloakViewModel) {
    val homePage by remember { viewModel.homePage }
    val items by remember { viewModel.items }
    val context = LocalContext.current
//    viewModel.getHomePage()

    viewModel.getItems()

    Column(
        modifier = Modifier.fillMaxSize().padding(vertical = 60.dp),
        verticalArrangement = Arrangement.Top
    ) {
//        LazyColumn(
//            contentPadding = PaddingValues(8.dp)
//        ) {
//            homePage?.let {
//                items(it.shelves) { item ->
//                    Text(text = item.type)
//                    Divider()
//                }
//            }
//        }

        items?.let {
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(items!!) { item ->
                    val onClick = {
                        startMiniApp(item.setting.miniAppID!!, context)
                    }
                    MiniCardRow(item = item, onClick)
                }
            }
        }

    }
}

@Composable
fun MiniCardRow(item: Item, onClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxSize()
            .background(Color.LightGray).border(BorderStroke(2.dp,Color.White))
            .padding(5.dp)
            .clickable(onClick = onClick)
    ) {
        item?.let {
            Image(
                painter = rememberAsyncImagePainter(it.thumb.url),
                contentDescription = "description",
                modifier = Modifier.size(64.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape(50)),
                contentScale = ContentScale.Crop,
            )
        }
        Column {
            Text(item.title)
            Text("Detail")
        }
    }
}
