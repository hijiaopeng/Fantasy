package com.fantasy.common_sdk.data

import com.fantasy.common_sdk.R

/**
 * 描述：
 *
 * @author JiaoPeng by 2021/10/27
 */
data class ToolbarParameter(
    var rightClickType: Int = 1,
    var backImg: Int = R.drawable.public_back,
    var showBack: Boolean = true,
    var titleStr: String = "",
    var titleColor: Int = R.color._01000D,
    var rightStr: String? = null,
    var rightColor: Int = R.color._01000D,
    var rightImg: Int? = null,
    var backgroundColor: Int? = null,
    var clearPadding: Boolean = false,
    var bounds: Float = 16f,
    var padding: Float = 20f,
    var drawablePadding: Float = 8f,
    var rightAction: ((Int) -> Unit)? = null,
    var leftAction: (() -> Unit)? = null
)
