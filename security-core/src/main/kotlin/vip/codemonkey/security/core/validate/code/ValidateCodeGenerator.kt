package vip.codemonkey.security.core.validate.code

import javax.servlet.http.HttpServletRequest

interface ValidateCodeGenerator {

    fun generate(request: HttpServletRequest):ValidateCode

}