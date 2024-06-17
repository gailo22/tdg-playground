package com.example.jetpack.keycloakloginapp.jsplugin

import com.example.jetpack.keycloaklogin.domain.usecases.GetBridgeTokenUseCase
import com.example.jetpack.keycloaklogin.domain.usecases.GetUserProfileUseCase
import com.tencent.tmfmini.sdk.annotation.JsEvent
import com.tencent.tmfmini.sdk.annotation.JsPlugin
import com.tencent.tmfmini.sdk.launcher.core.model.RequestEvent
import com.tencent.tmfmini.sdk.launcher.core.plugins.BaseJsPlugin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject


@JsPlugin(secondary = true)
class WxApiPlugin(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getBridgeTokenUseCase: GetBridgeTokenUseCase
) : BaseJsPlugin() {

    @JsEvent("login")
    fun login(req: RequestEvent) {

        //TODO: call get bridge-token api
        // POST: http://localhost:1337/api/tx-auth/login
        // {
        //    "subjectToken": "ey.."
        // }

        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                val bridgeToken = getBridgeTokenUseCase.invoke()
                val jsonObject = JSONObject()

                jsonObject.put("key", "wx.login")
                jsonObject.put("code", bridgeToken)
                req.ok(jsonObject)
            }
        }
    }

    @JsEvent("getUserProfile")
    fun getUserProfile(req: RequestEvent) {

        //TODO: call get user-profile api GET http://localhost:1337/api/tx-auth/user-profile
        // params: Bearer access token
        // response: user profile

        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                val userProfile = getUserProfileUseCase.invoke()
                val jsonObject = JSONObject()

                jsonObject.put("key", "wx.getUserProfile")
                jsonObject.put("userProfile", userProfile)
                req.ok(jsonObject)
            }
        }
    }

    @JsEvent("getUserInfo")
    fun getUserInfo(req: RequestEvent) {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("key", "wx.getUserInfo")
            jsonObject.put("msg", "this is getUserInfo XXX")
            val userInfo = JSONObject()
            userInfo.put("nickName", "John")
            userInfo.put("avatarUrl", "https://google.com/johnwick")
            jsonObject.put("userInfo", userInfo)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        req.ok(jsonObject)
    }
}