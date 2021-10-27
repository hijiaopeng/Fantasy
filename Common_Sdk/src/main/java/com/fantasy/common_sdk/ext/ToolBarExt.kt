package com.fantasy.common_sdk.ext

import android.view.View
import android.widget.TextView
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ClickUtils
import com.blankj.utilcode.util.ColorUtils
import com.fantasy.common_sdk.R
import com.fantasy.common_sdk.data.ToolbarParameter

/**
 * 描述：
 *
 * @author JiaoPeng by 2021/10/27
 */
/**
 * 初始化有返回键的toolbar
 */
fun View.initToolBar(
    toolbarWhite: ToolbarParameter
): View {
    val left = this.findViewById<TextView>(R.id.public_toolbar_left)
    val right = this.findViewById<TextView>(R.id.public_toolbar_right)
    val title = this.findViewById<TextView>(R.id.public_toolbar_title)
    ClickUtils.expandClickArea(left, 40)
    left.text2StartImg(toolbarWhite.backImg, toolbarWhite.bounds, toolbarWhite.padding)
    left.visibleOrGone(toolbarWhite.showBack)
    title.text(toolbarWhite.titleStr)
    toolbarWhite.backgroundColor?.let {
        this.setBackgroundColor(it.getColor())
    }
    title.setTextColor(ColorUtils.getColor(toolbarWhite.titleColor))
    if (toolbarWhite.rightStr != null && toolbarWhite.rightImg == null)
        right.text2ClearDrawables(
            toolbarWhite.rightStr
                ?: "", toolbarWhite.rightColor, toolbarWhite.padding
        )
    else if (toolbarWhite.rightStr == null && toolbarWhite.rightImg != null) {
        right.text2EndImg(
            toolbarWhite.rightImg!!,
            bounds = toolbarWhite.bounds,
            padding = toolbarWhite.padding,
            clearPadding = toolbarWhite.clearPadding
        )
    } else if (toolbarWhite.rightStr != null && toolbarWhite.rightImg != null) {
        right.text2EndImgAndText(
            toolbarWhite.rightImg!!,
            toolbarWhite.rightStr
                ?: "",
            color = toolbarWhite.rightColor,
            toolbarWhite.bounds,
            toolbarWhite.drawablePadding
        )
    }

    left.onSingleClick {
        if (toolbarWhite.leftAction == null) {
            ActivityUtils.getTopActivity().finish()
        } else {
            toolbarWhite.leftAction?.invoke()
        }
    }
    right.onSingleClick {
        toolbarWhite.rightAction?.invoke(toolbarWhite.rightClickType)
    }
    return this
}