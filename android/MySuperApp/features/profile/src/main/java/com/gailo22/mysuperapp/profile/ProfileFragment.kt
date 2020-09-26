package com.gailo22.mysuperapp.profile

import com.gailo22.mysuperapp.navigation.extensions.replaceFragment
import com.gailo22.mysuperapp.navigation.features.ProfileNavigation
import com.gailo22.mysuperapp.views.screen.ContainerFragment
import com.gailo22.mysuperapp.views.screen.simpleController
import com.gailo22.mysuperapp.views.secondaryButton

/**
 * @see com.gailo22.mysuperapp.navigation.features.ProfileNavigation.profile
 */
@Suppress("Unused")
class ProfileFragment : ContainerFragment() {
    override fun controller() = simpleController {
        profileHeaderRow {
            id("header")
            name("Mario")
            goals("32 goals completed!")
        }

        secondaryButton {
            id("name")
            text(R.string.name)
            clickListener { _ -> replaceFragment(ProfileNavigation.fragment(NameFragment::class)) }
        }
    }
}
