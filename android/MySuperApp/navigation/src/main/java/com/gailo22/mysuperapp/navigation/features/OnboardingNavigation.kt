package com.gailo22.mysuperapp.navigation.features

import com.gailo22.mysuperapp.navigation.core.Navigator

object OnboardingNavigation : Navigator() {
    fun intro() = fragmentIntent("com.sanogueralorenzo.onboarding.IntroFragment")
}
