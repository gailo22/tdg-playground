package com.gailo22.mysuperapp.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewPropertyAnimator
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.gailo22.mysuperapp.navigation.features.ProfileNavigation
import com.gailo22.mysuperapp.views.extensions.onFragmentBackCallback
import com.gailo22.mysuperapp.views.extensions.sendIntent
import com.gailo22.mysuperapp.views.screen.removeOverScroll
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @see com.gailo22.mysuperapp.navigation.features.HomeNavigation.home
 */
@Suppress("Unused")
class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager.removeOverScroll()
        viewPager.adapter = stateAdapter()
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                // appBarLayout.setExpanded(true, true)
                homeToolbar.onPageSelected(position)
                animateFabPageSelected(position)
            }
        })
        homeToolbar.onGoalsClick { viewPager.currentItem = GOAL_POSITION }
        homeToolbar.onCalendarClick { viewPager.currentItem = CALENDAR_POSITION }
        homeToolbar.onProfileClick { viewPager.currentItem = PROFILE_POSITION }
        fab.setOnClickListener {
            when (viewPager.currentItem) {
                GOAL_POSITION -> { }
                CALENDAR_POSITION -> { }
                else -> startActivity(
                    Intent.createChooser(
                        sendIntent(getString(R.string.share_app_text)), null
                    )
                )
            }
        }

        onFragmentBackCallback(
            callback = { viewPager.currentItem = GOAL_POSITION },
            predicate = { viewPager.currentItem != GOAL_POSITION }
        )
    }

    private fun animateFabPageSelected(position: Int) = when (position) {
        GOAL_POSITION -> fab.animate(R.drawable.ic_add)
        CALENDAR_POSITION -> fab.animate(R.drawable.ic_search)
        else -> fab.animate(R.drawable.ic_share)
    }

    private fun FloatingActionButton.animate(@DrawableRes image: Int) {
        this.scale(0F).withEndAction {
            this.setImageDrawable(ContextCompat.getDrawable(context!!, image))
            this.scale(1F).start()
        }.start()
    }

    private fun FloatingActionButton.scale(value: Float): ViewPropertyAnimator =
        this.animate().scaleX(value).scaleY(value).setDuration(FAB_SCALE_DURATION)

    private fun stateAdapter(): FragmentStateAdapter = object : FragmentStateAdapter(this) {
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment = when (position) {
            GOAL_POSITION -> GoalFragment()
            CALENDAR_POSITION -> DummyFragment()
            PROFILE_POSITION -> ProfileNavigation.profile()
            else -> error("unhandled position")
        }
    }

    private companion object {
        const val GOAL_POSITION = 0
        const val CALENDAR_POSITION = 1
        const val PROFILE_POSITION = 2
        const val FAB_SCALE_DURATION = 100L
    }
}
