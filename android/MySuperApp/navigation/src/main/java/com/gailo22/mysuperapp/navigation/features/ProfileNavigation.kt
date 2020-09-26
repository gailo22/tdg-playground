package com.gailo22.mysuperapp.navigation.features

import com.gailo22.mysuperapp.navigation.core.Navigator

object ProfileNavigation : Navigator() {
    fun profile() = fragment("com.gailo22.mysuperapp.profile.ProfileFragment")
}
