package com.gailo22.mysuperapp.views.composable

import com.airbnb.epoxy.EpoxyController
import com.gailo22.mysuperapp.views.ImageRowModelBuilder
import com.gailo22.mysuperapp.views.ImageRowModel_
import com.gailo22.mysuperapp.views.ImageTextRowModelBuilder
import com.gailo22.mysuperapp.views.ImageTextRowModel_
import com.gailo22.mysuperapp.views.SpaceRowModelBuilder
import com.gailo22.mysuperapp.views.SpaceRowModel_
import com.gailo22.mysuperapp.views.TextRowModelBuilder
import com.gailo22.mysuperapp.views.TextRowModel_
import com.gailo22.mysuperapp.views.textinput.TextInputLayoutRowModelBuilder
import com.gailo22.mysuperapp.views.textinput.TextInputLayoutRowModel_

/**
 * Model functions for EpoxyModelGroups to achieve similar DSL functionality on epoxy controllers
 */
inline fun EpoxyController.spaceRowModel(modelInitializer: SpaceRowModelBuilder.() -> Unit): SpaceRowModel_ =
    SpaceRowModel_().apply { modelInitializer() }

inline fun EpoxyController.textRowModel(modelInitializer: TextRowModelBuilder.() -> Unit): TextRowModel_ =
    TextRowModel_().apply { modelInitializer() }

inline fun EpoxyController.imageRowModel(modelInitializer: ImageRowModelBuilder.() -> Unit): ImageRowModel_ =
    ImageRowModel_().apply { modelInitializer() }

inline fun EpoxyController.imageTextRowModel(modelInitializer: ImageTextRowModelBuilder.() -> Unit):
    ImageTextRowModel_ = ImageTextRowModel_().apply { modelInitializer() }

inline fun EpoxyController.textInputLayoutRowModel(
    modelInitializer: TextInputLayoutRowModelBuilder.() -> Unit
): TextInputLayoutRowModel_ = TextInputLayoutRowModel_().apply { modelInitializer() }
