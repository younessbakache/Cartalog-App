package com.vitor238.cartalog.utils

import android.content.res.Configuration
import android.view.View
import androidx.core.content.ContextCompat
import com.vitor238.cartalog.R

object PreferencesUtils {
    fun setupBackgroundColor(view: View?) {
        val backgroundColor =
            when (view?.context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    R.color.black
                }
                else -> {
                    R.color.light_gray
                }
            }
        view?.setBackgroundColor(ContextCompat.getColor(view.context, backgroundColor))
    }
}