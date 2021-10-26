package com.fantasy.common_sdk.base

import androidx.lifecycle.ViewModel
import com.fantasy.common_sdk.event.EventLiveData
import com.fantasy.common_sdk.http.LoadStatusEntity
import com.kunminx.architecture.ui.callback.UnPeekLiveData

/**
 * 描述：
 *
 * @author JiaoPeng by 4/30/21
 */
open class BaseViewModel : ViewModel() {

    val loadingChange: UiLoadingChange by lazy { UiLoadingChange() }

    inner class UiLoadingChange {
        //显示加载框
        val showDialog by lazy { EventLiveData<String>() }
        //隐藏
        val dismissDialog by lazy { EventLiveData<Boolean>() }

        val showError by lazy { UnPeekLiveData<LoadStatusEntity>() }
    }

}