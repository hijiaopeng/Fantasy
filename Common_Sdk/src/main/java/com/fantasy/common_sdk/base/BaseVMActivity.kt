package com.fantasy.common_sdk.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.fantasy.common_sdk.R
import com.fantasy.common_sdk.event.AppViewModel
import com.fantasy.common_sdk.ext.dismissLoadingExt
import com.fantasy.common_sdk.ext.getAppViewModel
import com.fantasy.common_sdk.ext.getVmClazz
import com.fantasy.common_sdk.ext.showLoadingExt
import com.fantasy.common_sdk.network.manager.NetState
import com.fantasy.common_sdk.network.manager.NetworkStateManager
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.immersionBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * 描述：
 *
 * @author JiaoPeng by 4/30/21
 */
abstract class BaseVMActivity<VM : BaseViewModel, VB : ViewBinding> : AppCompatActivity(),
    CoroutineScope {
    /**
     * ViewModel对象
     */
    lateinit var mViewModel: VM

    lateinit var mViewBinding: VB

    //Application全局的ViewModel
    val appViewModel: AppViewModel by lazy { getAppViewModel() }

    /**
     * 对ViewBinding进行封装
     */
    abstract fun viewBinding(): VB

    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 创建LiveData数据观察者
     */
    abstract fun createObserver()

    private val mJob = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + mJob

    open lateinit var mCoroutineScope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = viewBinding()
        mViewModel = ViewModelProvider(this)[getVmClazz(this)]
        setContentView(mViewBinding.root)
        initImmersionBar()
        mCoroutineScope = CoroutineScope(coroutineContext)
        ARouter.getInstance().inject(this)
        registerUiChange()
        initView(savedInstanceState)
        createObserver()
        NetworkStateManager.instance.mNetworkStateCallback.observe(this) {
            onNetworkStateChanged(it)
        }
    }

    /**
     * 状态栏字体是否为黑色
     */
    open fun isDarkFont(): Boolean = true

    /**
     * 设置沉浸式
     */
    private fun initImmersionBar() {
        mViewBinding.root.findViewById<View>(R.id.public_toolbar)?.let {
            ImmersionBar.with(this)
                .titleBar(it)
                .statusBarDarkFont(isDarkFont(), 0.2f)
                .navigationBarColor(R.color.transparent)
                .init()
        }
    }

    /**
     * 注册UI 事件
     */
    private fun registerUiChange() {
        //显示弹窗
        mViewModel.loadingChange.showDialog.observe(this) {
            showLoading(it)
        }
        //关闭弹窗
        mViewModel.loadingChange.dismissDialog.observe(this) {
            dismissLoading()
        }
    }

    /**
     * 显示弹窗
     */
    open fun showLoading(message: String = "请求网络中...") {
        showLoadingExt(message)
    }

    /**
     * 关闭弹窗
     */
    open fun dismissLoading() {
        dismissLoadingExt()
    }

    /**
     * 网络变化监听
     */
    open fun onNetworkStateChanged(netState: NetState) {}

    override fun onDestroy() {
        super.onDestroy()
        mJob.cancel()
    }

}