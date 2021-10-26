package com.fantasy.common_sdk.base

import android.app.Application
import android.content.BroadcastReceiver
import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.decode.VideoFrameDecoder
import coil.fetch.VideoFrameFileFetcher
import coil.fetch.VideoFrameUriFetcher
import coil.util.DebugLogger
import android.content.IntentFilter
import com.alibaba.android.arouter.launcher.ARouter
import com.fantasy.common_sdk.BuildConfig
import com.fantasy.common_sdk.network.manager.NetworkStateReceive


/**
 * 描述：
 *
 * @author JiaoPeng by 2021/10/19
 */

val appContext: Application by lazy { BaseApplication.app }

open class BaseApplication : MultiDexApplication(), ImageLoaderFactory, ViewModelStoreOwner {

    private var mReceiver: BroadcastReceiver? = null

    companion object {
        lateinit var app: Application
    }

    private lateinit var mAppViewModelStore: ViewModelStore

    private var mFactory: ViewModelProvider.Factory? = null

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        MultiDex.install(this)
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
        mAppViewModelStore = ViewModelStore()
        initNetWork()
    }

    /**
     * 获取一个全局的ViewModel
     */
    fun getAppViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this, this.getAppFactory())
    }

    private fun getAppFactory(): ViewModelProvider.Factory {
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        }
        return mFactory as ViewModelProvider.Factory
    }

    /**
     * Application结束时调用
     */
    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()
        //注销网络监听广播
        unregisterReceiver(mReceiver)
    }

    /**
     * 配置Coil图片加载
     */
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this@BaseApplication)
            .availableMemoryPercentage(0.25) // Use 25% of the application's available memory.
            .crossfade(true) // Show a short crossfade when loading images from network or disk.
            .componentRegistry {
                // GIFs
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder(this@BaseApplication))
                } else {
                    add(GifDecoder())
                }

                // SVGs
                add(SvgDecoder(this@BaseApplication))

                // Video frames
                add(VideoFrameFileFetcher(this@BaseApplication))
                add(VideoFrameUriFetcher(this@BaseApplication))
                add(VideoFrameDecoder(this@BaseApplication))
            }
            .apply {
                if (BuildConfig.DEBUG) {
                    logger(DebugLogger(Log.VERBOSE))
                }
            }
            .build()
    }

    /**
     * 注册网络监听广播
     */
    private fun initNetWork() {
        mReceiver = NetworkStateReceive()
        val intentFilter = IntentFilter()
        intentFilter.addAction("net")
        registerReceiver(mReceiver, intentFilter)
    }

}