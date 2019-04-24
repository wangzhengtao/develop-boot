package vip.codemonkey.common.dto

import org.springframework.http.HttpStatus
import vip.codemonkey.common.annotation.NoArg
import java.io.Serializable


/**
 *@author wang zhengtao
 *
 */
@NoArg
data class SimpleResponse<T:Any?>(
    var code:Int = HttpStatus.OK.value(),
    var message:String = "SUCCESS",
    var content:T? = null,
    var success:Boolean = false
):Serializable{

    companion object{
        private val DEFAULT_ERROR_CODE: Int = HttpStatus.INTERNAL_SERVER_ERROR.value()
        private val DEFAULT_SUCCESS_CODE: Int = HttpStatus.OK.value()
        private val BUSINESS_FAILD_CODE: Int = 520
        private val DEFAULT_SUCCESS_MESSAGE: String = "SUCCESS"

        fun <T: Any?>error(message:String): SimpleResponse<T> {
            return SimpleResponse(
                DEFAULT_ERROR_CODE,
                message
            )
        }

        fun <T: Any?>failed(message: String): SimpleResponse<T> {
            return SimpleResponse(
                BUSINESS_FAILD_CODE,
                message
            )
        }

        fun <T: Any?>success(message:String): SimpleResponse<T> {
            return SimpleResponse(
                DEFAULT_SUCCESS_CODE,
                message,
                success = true
            )
        }

        fun <T: Any?>success(content:T): SimpleResponse<T> {
            return SimpleResponse(
                DEFAULT_SUCCESS_CODE,
                DEFAULT_SUCCESS_MESSAGE,
                content,
                success = true
            )
        }

        fun <T: Any?>success(message:String,content:T): SimpleResponse<T> {
            return SimpleResponse(
                DEFAULT_SUCCESS_CODE,
                message,
                content,
                success = true
            )
        }


        fun <T: Any?>paramInvalid(message: String): SimpleResponse<T> {
            return SimpleResponse(HttpStatus.BAD_REQUEST.value(), message)
        }

        fun <T: Any?>paramInvalid(): SimpleResponse<T> {
            return paramInvalid("请求参数不正确")
        }

        fun <T: Any?>unauthorized(): SimpleResponse<T> {
            return SimpleResponse(HttpStatus.UNAUTHORIZED.value(), "用户未登陆认证")
        }

        fun <T: Any?>forbidden(): SimpleResponse<T> {
            return SimpleResponse(HttpStatus.FORBIDDEN.value(), "未授权的请求")
        }
    }


}