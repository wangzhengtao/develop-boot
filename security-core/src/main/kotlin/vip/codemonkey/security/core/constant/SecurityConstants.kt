package vip.codemonkey.security.core.constant

class SecurityConstants {
    companion object{
        /**默认登录页面*/
        const val DEFAULT_LOGIN_PAGE_URL = "/stand-signIn.html"
        /**未登陆认证时，默认跳转url*/
        const val DEFAULT_UNAUTHENTICATION_URL:String = "/authentication/require"
        /**默认表单登陆url*/
        const val DEFAULT_LOGIN_PROCESSING_URL_FORM: String = "/authentication/form"
        /**请求验证码url*/
        const val GET_VALIDATE_CODE_URL: String = "/validate/code"
        /**
         * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
         */
        val DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode"
        /**
         * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
         */
        val DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode"
    }
}