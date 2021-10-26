package com.fantasy.common_sdk.http

abstract class BaseResponse<T> {

    abstract fun isSuccess(): Boolean

    abstract fun getResponseData(): T

    abstract fun getResponseCode(): Int

    abstract fun getResponseMsg(): String

}