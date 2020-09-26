package com.gailo22.mysuperapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gailo22.mysuperapp.navigation.features.HomeNavigation
import com.gailo22.mysuperapp.navigation.features.OnboardingNavigation
import com.gailo22.mysuperapp.usermanager.UserManager

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(0, 0)
        super.onCreate(savedInstanceState)
        when {
            UserManager().newUser -> startActivity(OnboardingNavigation.intro())
            else -> startActivity(HomeNavigation.home())
        }
        finish()
    }
}
