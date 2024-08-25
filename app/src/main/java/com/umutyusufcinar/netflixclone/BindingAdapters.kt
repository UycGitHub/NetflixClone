package com.umutyusufcinar.netflixclone

import android.view.View
import androidx.databinding.BindingAdapter

object BindingAdapters{
    @JvmStatic
    @BindingAdapter("isVisible")
    fun display(view: View, isVisible: Boolean){
        view.visibility = if(isVisible) View.VISIBLE else View.GONE
    }
}