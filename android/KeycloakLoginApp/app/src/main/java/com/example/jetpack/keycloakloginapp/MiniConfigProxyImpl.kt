package com.example.jetpack.keycloakloginapp

import android.app.Application
import com.tencent.tmf.mini.api.bean.MiniInitConfig
import com.tencent.tmf.mini.api.proxy.MiniConfigProxy
import com.tencent.tmfmini.sdk.annotation.ProxyService

@ProxyService(proxy = MiniConfigProxy::class)
class MiniConfigProxyImpl: MiniConfigProxy() {

    override fun getApp(): Application {
        return KeycloakLoginApp.instance
    }

    override fun buildConfig(): MiniInitConfig {
        val builder = MiniInitConfig.Builder()
        val config = builder
            .configAssetName("tcmpp-android-configurations.json")
            .build()
        return config
    }
}