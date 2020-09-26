package com.gailo22.mysuperapp.views.screen

import com.airbnb.epoxy.AsyncEpoxyController
import com.airbnb.epoxy.EpoxyController
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.withState

/**
 * For use with [ContainerFragment.controller].
 *
 * This builds Epoxy models in a background thread.
 */
open class SimpleController(
    val buildModelsCallback: EpoxyController.() -> Unit = {}
) : AsyncEpoxyController() {

    override fun buildModels() {
        buildModelsCallback()
    }
}

/**
 * Create a [SimpleController] that builds models with the given callback.
 */
fun ContainerFragment.simpleController(
    buildModels: EpoxyController.() -> Unit
) = SimpleController {
    // Models are built asynchronously, so it is possible that this is called after the fragment
    // is detached under certain race conditions.
    if (view == null || isRemoving) return@SimpleController
    buildModels()
}

/**
 * Create a [SimpleController] that builds models with the given callback.
 * When models are built the current state of the viewmodel will be provided.
 */
fun <S : MvRxState, A : BaseMvRxViewModel<S>> ContainerFragment.simpleController(
    viewModel: A,
    buildModels: EpoxyController.(state: S) -> Unit
) = SimpleController {
    if (view == null || isRemoving) return@SimpleController
    withState(viewModel) { state ->
        buildModels(state)
    }
}
