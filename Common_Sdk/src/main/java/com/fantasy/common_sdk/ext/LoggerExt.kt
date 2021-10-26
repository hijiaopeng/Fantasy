package com.fantasy.common_sdk.ext

import com.orhanobut.logger.Logger

/**
 * 描述：Log扩展函数
 *
 * @author JiaoPeng by 2021/10/20
 */
fun String.logD() {
    Logger.d(this)
}

fun String.logE() {
    Logger.e(this)
}

fun String.logW() {
    Logger.w(this)
}

fun String.logV() {
    Logger.v(this)
}

fun String.logI() {
    Logger.i(this)
}

fun String.logWtf() {
    Logger.wtf(this)
}