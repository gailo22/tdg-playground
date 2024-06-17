package com.example.jetpack.keycloakloginapp.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.jetpack.keycloakloginapp.CLIENT_ID
import com.example.jetpack.keycloakloginapp.CLIENT_SECRET
import com.example.jetpack.keycloakloginapp.keycloak.KeycloakActivity
import com.example.jetpack.keycloakloginapp.ui.theme.KeycloakLoginAppTheme
import com.tencent.tmf.mini.api.TmfMiniSDK
import com.tencent.tmf.mini.api.bean.MiniApp
import com.tencent.tmf.mini.api.bean.MiniCode
import com.tencent.tmf.mini.api.bean.MiniInitConfig
import com.tencent.tmf.mini.api.bean.MiniScene
import com.tencent.tmf.mini.api.bean.MiniStartOptions
import dagger.hilt.android.AndroidEntryPoint
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ClientSecretBasic
import net.openid.appauth.ResponseTypeValues

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewmodel: MainViewModel by viewModels()
    private lateinit var service: AuthorizationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        service = AuthorizationService(this)

        setContent {
            KeycloakLoginAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(onClick = { keycloakAuth() }) {
                            Text(text = "Login with Keycloak")
                        }
                    }
                }
            }
        }
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK) {
            val ex = AuthorizationException.fromIntent(it.data!!)
            val result = AuthorizationResponse.fromIntent(it.data!!)

            if (ex != null){
                Log.e("Keycloak Auth", "launcher: $ex")
            } else {
                val secret = ClientSecretBasic(CLIENT_SECRET)
                val tokenRequest = result?.createTokenExchangeRequest()

                service.performTokenRequest(tokenRequest!!, secret) {res, exception ->
                    if (exception != null){
                        Log.e("Keycloak Auth", "launcher: ${exception.error}" )
                    } else {
                        val token = res?.accessToken
                        viewmodel.setToken(token!!)

                        Log.d("Keycloak Auth", "Token: ${token}")

                        // Move to Keycloak screen
                        val intent = Intent(this, KeycloakActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }

    private fun keycloakAuth() {
        val redirectUri = Uri.parse("com.example.jetpack.keycloakloginapp://oauth2redirect")
        val authorizeUri = Uri.parse("https://keycloak.montree.me/realms/my-auth/protocol/openid-connect/auth")
        val tokenUri = Uri.parse("https://keycloak.montree.me/realms/my-auth/protocol/openid-connect/token")

        val config = AuthorizationServiceConfiguration(authorizeUri, tokenUri)
        val request = AuthorizationRequest
            .Builder(config, CLIENT_ID, ResponseTypeValues.CODE, redirectUri)
            .setScopes("email openid profile")
            .build()

        val intent = service.getAuthorizationRequestIntent(request)
        launcher.launch(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        service.dispose()
    }

}
