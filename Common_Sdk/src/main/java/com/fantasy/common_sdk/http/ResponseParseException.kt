package com.fantasy.common_sdk.http

import java.io.IOException

/**
 * 描述：
 *
 * @author {Wang Peng} by 2021/6/22
 */
 class ResponseParseException(
     message: String,
    val errorCode: String,
    val data: String? = null
) :
    IOException(message) {

}