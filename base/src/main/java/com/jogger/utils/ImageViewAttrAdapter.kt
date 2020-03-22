package com.jogger.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * Created by jogger on 2020/3/20
 * 描述：
 */
object ImageViewAttrAdapter {
    @BindingAdapter("android:src")
    @JvmStatic
    fun setSrc(view: ImageView, bitmap: Bitmap) {
        view.setImageBitmap(bitmap)
    }

    @BindingAdapter("android:src")
    @JvmStatic
    fun setSrc(view: ImageView, resId: Int) {
        view.setImageResource(resId)
    }

    @BindingAdapter("app:imageUrl")
    @JvmStatic
    fun loadImage(view: ImageView, url: String?) {
        Glide.with(view)
            .load(url)
            .into(view)
    }

    @BindingAdapter("app:imageUrl", "app:placeHolder")
    @JvmStatic
    fun loadImage(view: ImageView, url: String?, drawable: Drawable) {
        Glide.with(view)
            .load(url)
            .placeholder(drawable)
            .into(view)
    }


    @BindingAdapter("app:imageUrl", "app:placeHolder", "app:error")
    @JvmStatic
    fun loadImage(view: ImageView, url: String?, drawable: Drawable, error: Drawable) {
        Glide.with(view)
            .load(url)
            .placeholder(drawable)
            .error(error)
            .into(view)
    }
}