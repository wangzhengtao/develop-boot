package vip.codemonkey.security.core.validate.code

import vip.codemonkey.security.core.properties.SecurityProperties
import vip.codemonkey.security.core.validate.code.image.ImageCode
import javax.servlet.http.HttpServletRequest

interface ValidateCodeGenerator {

    fun generate(request: HttpServletRequest):ImageCode

}