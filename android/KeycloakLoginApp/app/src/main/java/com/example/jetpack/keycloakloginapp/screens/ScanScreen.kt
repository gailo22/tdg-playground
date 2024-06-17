package com.example.jetpack.keycloakloginapp.screens

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jetpack.keycloakloginapp.keycloak.QrCodeScanner
import com.example.jetpack.keycloakloginapp.ui.theme.lightestGray
import com.tencent.tmf.mini.api.TmfMiniSDK
import com.tencent.tmf.mini.api.bean.MiniApp
import com.tencent.tmf.mini.api.bean.MiniCode
import com.tencent.tmf.mini.api.bean.MiniScene
import com.tencent.tmf.mini.api.bean.MiniStartOptions
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.reflect.KSuspendFunction0

@Composable
fun ScanScreen(qrCodeScanner: QrCodeScanner) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QrCodeScan(qrCodeScanner)
    }
}

fun parseParameterValue(rawQuery: String, parameterName: String): String? {
    return rawQuery.split('&').map {
        val parts = it.split('=')
        val name = parts.firstOrNull() ?: ""
        val value = parts.drop(1).firstOrNull() ?: ""
        Pair(name, value)
    }.firstOrNull { it.first == parameterName }?.second
}

@Composable
fun QrCodeScan(qrCodeScanner: QrCodeScanner) {
    val context = LocalContext.current
    val qrCodeResults = qrCodeScanner.qrCodeResults.collectAsStateWithLifecycle()
    ScanQrCode(
        qrCodeScanner::startScan,
        qrCodeResults.value
    ) {
        it?.let {
            //tcmpp://applet/?appId=mp4zcrk88it22nl6&type=2&businessId=wfog8nsphkj6bl4vlk&timestamp=1713839819
            val params = it.split("?").last()
            val appId = parseParameterValue(params, "appId")
            appId?.let {
                startMiniApp(appId, context)
            }
        }
    }
}

fun Context.getActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

fun startMiniApp(appId: String, context: Context) {
    Timber.d("appId: $appId")

    val mResultReceiver: ResultReceiver = object : ResultReceiver(Handler()) {
        override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
            if (resultCode != MiniCode.CODE_OK) {
                val errMsg = resultData.getString(MiniCode.KEY_ERR_MSG)
                println("$resultCode $errMsg")
                Toast.makeText(context, errMsg + resultCode, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show()
            }
        }
    }

    val miniStartOptions = MiniStartOptions()
    miniStartOptions.resultReceiver = mResultReceiver
    TmfMiniSDK.startMiniApp(context.getActivity(), appId,
        MiniScene.LAUNCH_SCENE_MAIN_ENTRY, MiniApp.TYPE_ONLINE, miniStartOptions)
}

@Composable
private fun ScanQrCode(
    onScanBarcode: suspend () -> String?,
    qrCodeValue: String?,
    action: suspend (String?) -> Unit
) {
    val scope = rememberCoroutineScope()

    Button(
        modifier = Modifier
            .fillMaxWidth(.85f),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black
        ),
        onClick = {
            scope.launch {
                val qrCode = async { onScanBarcode() }.await()
                action(qrCode)
            }
        }) {
        Text(
            text = "Scan QR",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayMedium,
            color = lightestGray,
        )
    }

    Spacer(modifier = Modifier.height(20.dp))

    Text(
        text = qrCodeValue ?: "0000000000",
        style = MaterialTheme.typography.displayMedium
    )
}