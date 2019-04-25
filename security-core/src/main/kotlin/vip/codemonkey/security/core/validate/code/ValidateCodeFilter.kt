package vip.codemonkey.security.core.validate.code

import org.apache.commons.lang.StringUtils
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.social.connect.web.HttpSessionSessionStrategy
import org.springframework.stereotype.Component
import org.springframework.util.AntPathMatcher
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.filter.OncePerRequestFilter
import vip.codemonkey.security.core.constant.SecurityConstants
import vip.codemonkey.security.core.validate.code.image.ImageCode
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component("validateCodeFilter")
class ValidateCodeFilter :OncePerRequestFilter(),InitializingBean {

    @Autowired
    lateinit var authenticationFailureHandler: AuthenticationFailureHandler
    val sessionStrategy = HttpSessionSessionStrategy()
    val antPathMatcher = AntPathMatcher()


    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        if(antPathMatcher.match(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM,request.requestURI)){
            try{
                validate(ServletWebRequest(request))
            }catch (e:ValidateCodeException){
                authenticationFailureHandler.onAuthenticationFailure(request,response,e)
                return
            }
        }

        filterChain.doFilter(request,response)
    }


    fun validate(request: ServletWebRequest){
        val sessionCode = sessionStrategy.getAttribute(request,ValidateCodeController.SESSION_KEY) as ImageCode?
        val requestCode = request.request.getAttribute("imageCode") as String?
        if(StringUtils.isBlank(requestCode)) {
            throw ValidateCodeException("验证码参数不能为空")
            sessionStrategy.removeAttribute(request,ValidateCodeController.SESSION_KEY)
        }

        if(Objects.isNull(sessionCode)){
            throw ValidateCodeException("验证码不存在")
            sessionStrategy.removeAttribute(request,ValidateCodeController.SESSION_KEY)
        }

        if(sessionCode!!.isExpire()) {
            throw ValidateCodeException("验证码已失效")
            sessionStrategy.removeAttribute(request,ValidateCodeController.SESSION_KEY)
        }

        if(!Objects.equals(requestCode,sessionCode.code)){
            throw ValidateCodeException("验证码不匹配")
            sessionStrategy.removeAttribute(request,ValidateCodeController.SESSION_KEY)
        }
    }
}