package com.fantasy.common_sdk.ext

import android.view.View
import com.blankj.utilcode.util.ClickUtils

/**
 * 描述：
 *
 * @author JiaoPeng by 4/29/21
 */
/**
 * View点击事件防抖
 */
fun View.onSingleClick(block: () -> Unit) = setOnClickListener {
    ClickUtils.applySingleDebouncing(this, 800) {
        block.invoke()
    }
}

/**
 * 根据条件设置view显示隐藏 为true 显示，为false 隐藏
 */
fun View?.visibleOrGone(flag: Boolean) {
    this?.visibility = if (flag) {
        View.VISIBLE
    } else {
        View.GONE
    }
}