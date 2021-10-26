package com.fantasy.common_sdk.http

data class ApiResponse<T>(
    val code: Int,
    val msg: String,
    var data: T,
    val pageInfo: PageInfo? = null
) : BaseResponse<T>() {

    override fun isSuccess() = code == 100000

    override fun getResponseCode() = code

    override fun getResponseData() = data

    override fun getResponseMsg() = msg

}


data class PageInfo(
    val pageNum: Int = 1,
    val pageSize: Int = 15,
    val pageCount: Int = 0,
    val total: Int = 0
)