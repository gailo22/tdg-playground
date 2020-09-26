package com.gailo22.mysuperapp.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.gailo22.mysuperapp.views.extensions.toDp
import kotlinx.android.synthetic.main.view_loading.view.*

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class LoadingRow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.view_loading, this)
    }

    @ModelProp
    @JvmOverloads
    fun size(size: Int = 48) {
        loading.minimumHeight = toDp(size)
    }
}
