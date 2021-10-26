package com.fantasy.common_sdk.http


data class LoadStatusEntity(
    var requestCode: String,//请求码
    var throwable: Throwable,//失败异常
    var errorCode: Int,//错误码
    var errorMessage: String,//错误消息
    var data: String? = null,//错误消息
    var loadingType: Boolean = false,// 请求时 loading 类型
    var intentData: Any? = null //请求时回调回来-->发起请求时携带的参数 示例场景：发起请求时传递一个position ,如果请求失败时，可能需要把这个position回调给 activity/fragment 根据position做错误处理
)