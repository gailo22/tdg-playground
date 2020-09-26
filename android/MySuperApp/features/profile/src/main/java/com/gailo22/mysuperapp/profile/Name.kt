package com.gailo22.mysuperapp.profile

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.mvrx.Async
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.PersistState
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.ViewModelContext
import com.airbnb.mvrx.fragmentViewModel
import com.gailo22.mysuperapp.usermanager.UserManager
import com.gailo22.mysuperapp.views.Keyboard
import com.gailo22.mysuperapp.views.PrimaryButton
import com.gailo22.mysuperapp.views.TextRow
import com.gailo22.mysuperapp.views.composable.cardContainer
import com.gailo22.mysuperapp.views.composable.textInputLayoutRowModel
import com.gailo22.mysuperapp.views.composable.textRowModel
import com.gailo22.mysuperapp.views.extensions.addHorizontalItemDecorator
import com.gailo22.mysuperapp.views.extensions.addVerticalItemDecorator
import com.gailo22.mysuperapp.views.screen.ContainerFragment
import com.gailo22.mysuperapp.views.screen.simpleController
import io.reactivex.Completable
import com.gailo22.mysuperapp.resources.R as G

class NameFragment : ContainerFragment() {

    private var initialInputBind = true
    private val viewModel: NameViewModel by fragmentViewModel(NameViewModel::class)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PrimaryButton(requireContext()).apply {
            get.text = getString(G.string.save)
            get.setOnClickListener { viewModel.onButtonClick() }
        }.let { bottomView.addView(it) }

        recyclerView.addVerticalItemDecorator()
        recyclerView.addHorizontalItemDecorator()
        (recyclerView.layoutManager as LinearLayoutManager).reverseLayout = true

        viewModel.asyncSubscribe(NameState::complete, uniqueOnly(), onSuccess = {
            Keyboard.hide(view)
            activity?.onBackPressed()
        })
    }

    override fun controller() = simpleController(viewModel) { state ->
        cardContainer(
            textRowModel {
                id("name_title")
                body(R.string.onboarding_name_title)
                style(TextRow.TextStyle.SUBTITLE)
            },
            textInputLayoutRowModel {
                id("name_input")
                body(state.name)
                inputType(android.text.InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                helperText(R.string.onboarding_name_helper_text)
                state.error?.let { error(it) } ?: error(null)
                textChangedListener { viewModel.onNameChanged(it) }
                onBind { _, view, _ ->
                    if (initialInputBind) {
                        Keyboard.show(view)
                        initialInputBind = false
                    }
                }
            }
        )
    }
}

class NameViewModel(
    state: NameState,
    private val userManager: UserManager = UserManager()
) : BaseMvRxViewModel<NameState>(state) {

    fun onNameChanged(name: String) = setState {
        val error = if (name.isBlank()) this.error else null
        copy(name = name, error = error)
    }

    fun onButtonClick() {
        withState {
            if (it.name.isNullOrBlank().not()) {
                userManager.name = it.name
                Completable.complete().execute { copy(complete = it) }
            } else {
                setState { copy(error = R.string.onboarding_name_error) }
            }
        }
    }

    companion object : MvRxViewModelFactory<NameViewModel, NameState> {

        override fun initialState(viewModelContext: ViewModelContext): NameState =
            NameState(UserManager().name)

        override fun create(
            viewModelContext: ViewModelContext,
            state: NameState
        ): NameViewModel = NameViewModel(state)
    }
}

data class NameState(
    @PersistState val name: String? = null,
    @PersistState val error: Int? = null,
    val complete: Async<Unit> = Uninitialized
) : MvRxState
