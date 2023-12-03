package com.foss.foss.util.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.foss.foss.R

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("app:imageUrl")
    fun setImageUrl(
        imageView: ImageView,
        imageUrl: String
    ) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .error(R.drawable.eight)
            .into(imageView)
    }
}
