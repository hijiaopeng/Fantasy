package com.fantasy.common_sdk.http

import androidx.lifecycle.rxLifeScope
import com.fantasy.common_sdk.base.BaseViewModel
import com.fantasy.common_sdk.ext.code
import com.fantasy.common_sdk.ext.data
import com.fantasy.common_sdk.ext.errorMessage
import com.fantasy.common_sdk.ext.logE
import kotlinx.coroutines.CoroutineScope

/**
 * 使用示例
 * fun getAddSearchRecord(request: SearchReq) {
rxHttpRequest {
onRequest = {
addSearchRecord.value = NetHotelApi.postAddSearchRecord(request).await().data
}
onError = {
addSearchRecord.value = it
}
requestCode = NetHotelApi.GET_ON_HOTEL_ADD_SEARCH
}
}
 */

fun BaseViewModel.rxHttpRequest(requestDslClass: HttpRequestDsl.() -> Unit) {
    val httpRequestDsl = HttpRequestDsl()
    requestDslClass(httpRequestDsl)
    rxLifeScope.launch({
        // 携程体方法执行工作
        httpRequestDsl.onRequest.invoke(this)
    }, {
        //请求失败时将错误日志打印一下 防止错哪里了都不晓得
        it.printStackTrace()
        "操！请求出错了----> ${it.message}".logE()
        val loadStatusEntity = LoadStatusEntity(
            httpRequestDsl.requestCode,
            it,
            it.code,
            it.errorMessage,
            it.data,
            httpRequestDsl.showLoading,
            httpRequestDsl.intentData
        )
        if (httpRequestDsl.onError == null) {
            loadingChange.showError.value = loadStatusEntity
        } else {
            httpRequestDsl.onError?.invoke(loadStatusEntity)
        }
    }, {
        if (httpRequestDsl.showLoading) {
            loadingChange.showDialog.value = httpRequestDsl.loadingMessage
        }
    }, {
        if (httpRequestDsl.showLoading) {
            loadingChange.dismissDialog.value = true
        }

    })

}


class HttpRequestDsl {
    /**
     * 请求工作 在这里执行网络接口请求，然后回调成功数据
     */
    var onRequest: suspend CoroutineScope.() -> Unit = {}

    /**
     * 错误回调，默认为null 如果你传递了他 那么就代表你请求失败的逻辑你自己处理
     */
    var onError: ((LoadStatusEntity) -> Unit)? = null

    /**
     * 目前这个只有在 loadingType == LOADING_DIALOG 的时候才有用 不是的话都不用传他
     */
    var loadingMessage: String = "请求网络中..."

    /**
     * 请求时loading类型 默认请求时不显示loading
     */
    var showLoading = false

    /**
     * 请求 code 请求错误时 需要根据该字段去判断到底是哪个请求做相关处理 可以用URL去标记
     */
    var requestCode: String = "mmp"

    /**
     * 请求时回调给发起请求时携带的参数 示例场景：发起请求时传递一个 position ,如果请求失败时，可能需要把这个position回调给 activity/fragment 根据position做错误处理
     */
    var intentData: Any? = null
}
