package jcp.apps.core

import android.content.Context


object CoreUtils {
    fun pxToDp(context: Context, px: Float): Float {
        val metrics = context.resources.displayMetrics
        return px / metrics.density
    }

    fun dpToPx(context: Context, dp: Float): Float {
        val metrics = context.resources.displayMetrics
        return dp * metrics.density
    }
}