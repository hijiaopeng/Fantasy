package com.fantasy.common_sdk.http

import okhttp3.Response
import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.exception.ParseException
import rxhttp.wrapper.parse.TypeParser
import rxhttp.wrapper.utils.convertTo
import java.io.IOException
import java.lang.reflect.Type

/**
 * 描述：
 *
 * @author JiaoPeng by 2021/10/20
 */
@Parser(name = "Response")
open class ResponseParser<T> : TypeParser<ApiResponse<T>> {

    protected constructor() : super()
    constructor(type: Type) : super(type)

    @Throws(IOException::class)
    override fun onParse(response: Response): ApiResponse<T> {
        val data: ApiResponse<T> = response.convertTo(ApiResponse::class, types[0])
        if (data.data == null && types[0] === String::class.java) {
            //判断我们传入的泛型是String对象，就给t赋值""字符串，确保t不为null
            data.data = "" as T
        }
        if (!data.isSuccess()) {
            throw ParseException(data.code.toString(), data.msg, response)
        }

        return data
    }

}