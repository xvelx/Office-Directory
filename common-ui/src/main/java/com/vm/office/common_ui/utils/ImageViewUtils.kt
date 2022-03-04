package com.vm.office.common_ui.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

/**
 * Custom (Data) Binding for the ImageView.
 *
 * Gets the image from the given URL and sets it to the ImageView.
 *
 * @param imageView target
 * @param url image URL
 */
@BindingAdapter("imageUrl")
fun setImageResource(imageView: ImageView, url: String?) {
    if (url != null) {
        Picasso.get().load(url).fit().into(imageView)
    }
}