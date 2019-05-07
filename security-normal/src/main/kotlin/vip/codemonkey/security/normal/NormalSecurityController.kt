package vip.codemonkey.security.normal

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vip.codemonkey.security.core.constant.SecurityConstants
import vip.codemonkey.security.core.dto.SimpleResponse
import vip.codemonkey.security.core.properties.LoginType
import vip.codemonkey.security.core.properties.SecurityProperties
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
class NormalSecurityController {
    private val logger = LoggerFactory.getLogger(NormalSecurityController::class.java)

    val requestCache = HttpSessionRequestCache()
    val redirectStrategy = DefaultRedirectStrategy()

    @Autowired
    lateinit var securityProperties: SecurityProperties
    @Autowired
    lateinit var objectMapper: ObjectMapper

    @RequestMapping(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
    fun requireAuthentication(request: HttpServletRequest, response: HttpServletResponse){
        val savedRequest = requestCache.getRequest(request, response)
        if(Objects.nonNull(savedRequest) && Objects.equals(LoginType.REDIRECT,securityProperties.normal.loginType)){
            redirectStrategy.sendRedirect(request,response,securityProperties.normal.loginPage)
        }else{
            response.setContentType("application/json;charset=UTF-8")
            response.getWriter().write(objectMapper.writeValueAsString(SimpleResponse.unauthorized()))
        }
    }
}