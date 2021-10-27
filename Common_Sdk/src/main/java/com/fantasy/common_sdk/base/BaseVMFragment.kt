package com.fantasy.common_sdk.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.fantasy.common_sdk.R
import com.fantasy.common_sdk.event.AppViewModel
import com.fantasy.common_sdk.ext.dismissLoadingExt
import com.fantasy.common_sdk.ext.getAppViewModel
import com.fantasy.common_sdk.ext.getVmClazz
import com.fantasy.common_sdk.ext.showLoadingExt
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.immersionBar

/**
 * 描述：
 *
 * @author JiaoPeng by 2021/5/8
 */
abstract class BaseVMFragment<VM : BaseViewModel, VB : ViewBinding> : Fragment() {

    //是否第一次加载
    private var isFirst: Boolean = true

    lateinit var mViewModel: VM

    lateinit var mViewBinding: VB

    lateinit var mActivity: AppCompatActivity

    //Application全局的ViewModel
    val appViewModel: AppViewModel by lazy { getAppViewModel() }

    abstract fun viewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun createObserver()

    /**
     * 懒加载
     */
    abstract fun lazyLoadData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewBinding = viewBinding(inflater, container)
        mViewBinding.root.findViewById<Toolbar>(R.id.public_toolbar)?.let {
            immersionBar {
                titleBar(it)
                statusBarDarkFont(isDarkFont(), 0.2f)
                navigationBarColor(R.color.transparent)
            }
        }
        return mViewBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isFirst = true
        mViewModel = ViewModelProvider(this)[getVmClazz(this)]
        initView(savedInstanceState)
        createObserver()
        registerUiChange()
        initData()
    }

    /**
     * 状态栏字体是否为黑色
     */
    open fun isDarkFont(): Boolean = true

    private fun registerUiChange() {
        mViewModel.loadingChange.showDialog.observe(this) {
            showLoading(it)
        }
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
     * Fragment执行onCreate后触发的方法
     */
    open fun initData() {}

    override fun onResume() {
        super.onResume()
        onVisible()
    }

    /**
     * 是否需要懒加载
     */
    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            //等待view加载后触发懒加载
            view?.post {
                lazyLoadData()
                isFirst = false
            }
        }
    }
}