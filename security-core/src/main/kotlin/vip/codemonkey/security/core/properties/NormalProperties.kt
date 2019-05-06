package vip.codemonkey.security.core.properties

import vip.codemonkey.security.core.constant.SecurityConstants

open class NormalProperties {
    var loginType:LoginType = LoginType.JSON
    val loginPage:String = SecurityConstants.DEFAULT_LOGIN_PAGE_URL
    val rememberMeSeconds:Long = 3600

}