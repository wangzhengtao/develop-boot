package vip.codemonkey.security.core.validate.code

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.social.connect.web.HttpSessionSessionStrategy
import org.springframework.web.bind.ServletRequestUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.ServletWebRequest
import vip.codemonkey.security.core.dto.SimpleResponse
import vip.codemonkey.security.core.properties.SecurityProperties
import vip.codemonkey.security.core.validate.code.image.ImageCode
import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.util.*
import javax.imageio.ImageIO
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
class ValidateCodeController {
    private val logger = LoggerFactory.getLogger(ValidateCodeController::class.java)
    companion object{
        const val SESSION_KEY = "image_validate_code"
    }

    @Autowired
    lateinit var securityProperties: SecurityProperties
    @Autowired
    lateinit var imageCodeGenerator:ValidateCodeGenerator

    val sessionStrategy = HttpSessionSessionStrategy()

    @GetMapping("/validate/code/image")
    fun validateCode(request:HttpServletRequest,response:HttpServletResponse): SimpleResponse<Any> {
        val imageCode = imageCodeGenerator.generate(request)
        logger.info("image code is {}",imageCode.code)
        sessionStrategy.setAttribute(ServletWebRequest(request),SESSION_KEY,imageCode)
        val byte = ByteArrayOutputStream().let {
            ImageIO.write(imageCode.image,"JPEG",it)
            it.flush()
            it
        }.toByteArray()
        return SimpleResponse.success("获取验证码成功",Base64.getEncoder().encodeToString(byte))
    }


}