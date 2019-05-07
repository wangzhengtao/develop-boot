package vip.codemonkey.security.core.validate.code

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.ServletWebRequest
import vip.codemonkey.security.core.constant.SecurityConstants
import vip.codemonkey.security.core.dto.SimpleResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
class ValidateCodeController {
    private val logger = LoggerFactory.getLogger(ValidateCodeController::class.java)

    @Autowired
    lateinit var validateCodeProcessor:MutableMap<String,ValidateCodeProcessor>
    @Autowired
    lateinit var objectMapper:ObjectMapper

    @RequestMapping(SecurityConstants.GET_VALIDATE_CODE_URL+"/{type}")
    fun validateCode(request:HttpServletRequest,response:HttpServletResponse,@PathVariable type:String){
        try{
            val key = type + "CodeProcessor"
            logger.info("send validate code type  {}",type)
            val validateCodeProcessor = validateCodeProcessor[key]?: throw ValidateCodeException("$key doesn't exit")
            validateCodeProcessor.create(ServletWebRequest(request,response))
        }catch (e:Exception){
            response.setContentType("application/json;charset=UTF-8")
            response.getWriter().write(objectMapper.writeValueAsString(SimpleResponse.failed<Any>("获取验证码失败:"+e.message)))
        }
    }


}