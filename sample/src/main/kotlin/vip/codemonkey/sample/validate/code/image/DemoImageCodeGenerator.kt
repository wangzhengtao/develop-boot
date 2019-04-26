package vip.codemonkey.sample.validate.code.image

import org.springframework.stereotype.Component
import vip.codemonkey.security.core.validate.code.ValidateCodeGenerator
import vip.codemonkey.security.core.validate.code.image.ImageCode
import javax.servlet.http.HttpServletRequest

//@Component("imageCodeGenerator")
class DemoImageCodeGenerator: ValidateCodeGenerator {

    override fun generate(request: HttpServletRequest): ImageCode {
        throw RuntimeException("demo iamge code generator doesn't work")
    }
}