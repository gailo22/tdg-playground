package com.gailo22.mysuperapp.views

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import kotlinx.android.synthetic.main.view_image_text.view.*

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class ImageTextRow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.view_image_text, this)
        title.setStyle(TextRow.TextStyle.SUBTITLE)
        subtitle.setStyle(TextRow.TextStyle.BODY)
    }

    @TextProp
    fun setTitle(text: CharSequence) {
        title.text = text
    }

    @TextProp
    fun setSubtitle(text: CharSequence?) {
        subtitle.text = text
    }

    @ModelProp
    fun setImage(@DrawableRes drawable: Int) {
        image.setDrawable(drawable)
    }

    @ModelProp
    fun setImageColor(@ColorRes color: Int?) {
        color?.let {
            ImageViewCompat.setImageTintList(
                image,
                ColorStateList.valueOf(ContextCompat.getColor(context, it))
            )
        }
    }
}
