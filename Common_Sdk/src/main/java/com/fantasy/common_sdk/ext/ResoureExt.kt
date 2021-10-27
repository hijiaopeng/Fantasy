package com.fantasy.common_sdk.ext

import android.graphics.drawable.Drawable
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ResourceUtils
import com.blankj.utilcode.util.StringUtils

fun Int.getDrawable(): Drawable = ResourceUtils.getDrawable(this)

fun Int.getString(): String = StringUtils.getString(this)

fun Int.getColor(): Int = ColorUtils.getColor(this)








