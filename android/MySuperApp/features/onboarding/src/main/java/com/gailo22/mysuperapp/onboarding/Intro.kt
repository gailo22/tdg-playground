package com.gailo22.mysuperapp.onboarding

import android.os.Bundle
import android.view.Gravity
import android.view.View
import com.airbnb.mvrx.Async
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.ViewModelContext
import com.airbnb.mvrx.fragmentViewModel
import com.gailo22.mysuperapp.navigation.features.HomeNavigation
import com.gailo22.mysuperapp.usermanager.UserManager
import com.gailo22.mysuperapp.views.MiniButton
import com.gailo22.mysuperapp.views.TextRow
import com.gailo22.mysuperapp.views.extensions.addHorizontalItemDecorator
import com.gailo22.mysuperapp.views.extensions.bottomPadding
import com.gailo22.mysuperapp.views.extensions.setTextWithLinks
import com.gailo22.mysuperapp.views.extensions.topBottomPadding
import com.gailo22.mysuperapp.views.extensions.urlIntent
import com.gailo22.mysuperapp.views.imageRow
import com.gailo22.mysuperapp.views.imageTextRow
import com.gailo22.mysuperapp.views.screen.ContainerFragment
import com.gailo22.mysuperapp.views.screen.simpleController
import com.gailo22.mysuperapp.views.spaceRow
import com.gailo22.mysuperapp.views.textRow
import io.reactivex.Completable
import com.gailo22.mysuperapp.resources.R as G

/**
 * @see com.gailo22.mysuperapp.navigation.features.OnboardingNavigation.intro
 */
@Suppress("Unused")
class IntroFragment : ContainerFragment() {
    private val viewModel: IntroViewModel by fragmentViewModel(IntroViewModel::class)

    private val terms: Pair<String, () -> Unit> by lazy {
        Pair(getString(R.string.terms),
            { startActivity(urlIntent(getString(R.string.terms_url))) })
    }
    private val privacy: Pair<String, () -> Unit> by lazy {
        Pair(getString(R.string.privacy),
            { startActivity(urlIntent(getString(R.string.privacy_url))) })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        TextRow(context!!).apply {
            gravity = Gravity.CENTER
            topBottomPadding(16)
            setStyle(TextRow.TextStyle.CAPTION)
            setTextWithLinks(getString(R.string.privacy_terms), terms, privacy)
        }.let { bottomView.addView(it) }

        MiniButton.create(context!!, getString(R.string.intro_button)) {
            viewModel.onButtonClick()
        }.let {
            it.bottomPadding(8)
            bottomView.addView(it)
        }

        recyclerView.addHorizontalItemDecorator(64)

        viewModel.asyncSubscribe(IntroState::complete, uniqueOnly(), onSuccess = {
            startActivity(HomeNavigation.home())
            activity?.finish()
        })
    }

    override fun controller() = simpleController {
        spaceRow {
            id("top_space")
            size(60)
        }
        imageRow {
            id("logo")
            drawable(G.drawable.ic_logo)
            height(80)
        }
        spaceRow {
            id("image_bottom_space")
            size(16)
        }
        textRow {
            id("title")
            body(R.string.intro_title)
            style(TextRow.TextStyle.HEADLINE)
            bodyGravity(Gravity.CENTER)
        }
        spaceRow {
            id("title_bottom_space")
            size(60)
        }
        imageTextRow {
            id("journey")
            image(G.drawable.ic_section_journal)
            title(getString(R.string.intro_journal_title))
            subtitle(getString(R.string.intro_journal_subtitle))
        }
        spaceRow {
            id("journey_bottom_space")
            size(32)
        }
        imageTextRow {
            id("habits")
            image(G.drawable.ic_section_habits)
            title(getString(R.string.intro_habits_title))
            subtitle(getString(R.string.intro_habits_subtitle))
        }
        spaceRow {
            id("habits_bottom_space")
            size(32)
        }
        imageTextRow {
            id("zoneout")
            image(G.drawable.ic_section_zoneout)
            title(getString(R.string.intro_zoneout_title))
            subtitle(getString(R.string.intro_zoneout_subtitle))
        }
    }
}

class IntroViewModel(
    state: IntroState,
    private val userManager: UserManager = UserManager()
) : BaseMvRxViewModel<IntroState>(state) {

    fun onButtonClick() {
        withState {
            userManager.newUser = false
            Completable.complete().execute { copy(complete = it) }
        }
    }

    companion object : MvRxViewModelFactory<IntroViewModel, IntroState> {

        override fun create(
            viewModelContext: ViewModelContext,
            state: IntroState
        ): IntroViewModel = IntroViewModel(state)
    }
}

data class IntroState(
    val complete: Async<Unit> = Uninitialized
) : MvRxState
