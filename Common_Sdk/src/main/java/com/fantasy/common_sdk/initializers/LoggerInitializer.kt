package com.fantasy.common_sdk.initializers

import android.content.Context
import androidx.startup.Initializer
import com.tencent.mmkv.MMKV
import com.orhanobut.logger.AndroidLogAdapter

import com.orhanobut.logger.PrettyFormatStrategy

import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger


/**
 * 描述：Log初始化
 *
 * @author JiaoPeng by 2021/19/10
 */
class LoggerInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(true) // (Optional) Whether to show thread info or not. Default true
            .methodCount(2) // (Optional) How many method line to show. Default 2
            .methodOffset(4) // (Optional) Hides internal method calls up to offset. Default 5
            .tag("Fantasy") // (Optional) Global tag for every log. Default PRETTY_LOGGER
            .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }

}