package vip.codemonkey.security.normal.authentication

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component
import vip.codemonkey.security.core.dto.SimpleResponse
import vip.codemonkey.security.core.properties.LoginType
import vip.codemonkey.security.core.properties.SecurityProperties
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component("customAuthenticationFailureHandler")
class CustomAuthenticationFailureHandler: SimpleUrlAuthenticationFailureHandler() {

    @Autowired
    lateinit var objectMapper: ObjectMapper
    @Autowired
    lateinit var securityProperties: SecurityProperties

    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        if(Objects.equals(securityProperties.normal.loginType, LoginType.JSON)){
            response.setContentType("application/json;charset=UTF-8")

            response.getWriter().write(objectMapper.writeValueAsString(SimpleResponse.failed<Any>(message = "${exception.message}")))
        }else{
            super.onAuthenticationFailure(request, response, exception)
        }
    }
}