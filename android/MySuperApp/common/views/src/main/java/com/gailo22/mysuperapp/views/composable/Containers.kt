package com.gailo22.mysuperapp.views.composable

import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelGroup
import com.gailo22.mysuperapp.views.R

fun EpoxyController.cardContainer(vararg models: EpoxyModel<*>) =
    EpoxyModelGroup(R.layout.container_card_view, models.toList()).addTo(this)
