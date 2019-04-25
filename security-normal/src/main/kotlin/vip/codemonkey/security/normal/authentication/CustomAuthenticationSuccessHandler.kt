package vip.codemonkey.security.normal.authentication

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import vip.codemonkey.security.core.dto.SimpleResponse
import vip.codemonkey.security.core.properties.LoginType
import vip.codemonkey.security.core.properties.SecurityProperties
import java.util.*

@Component("customAuthenticationSuccessHandler")
class CustomAuthenticationSuccessHandler: SavedRequestAwareAuthenticationSuccessHandler() {

    @Autowired
    lateinit var objectMapper: ObjectMapper
    @Autowired
    lateinit var securityProperties: SecurityProperties

    override fun onAuthenticationSuccess(
        request: javax.servlet.http.HttpServletRequest,
        response: javax.servlet.http.HttpServletResponse,
        authentication: Authentication
    ) {
        logger.info("login success ...")
        if(Objects.equals(securityProperties.normal.loginType, LoginType.JSON)){
            response.setContentType("application/json;charset=UTF-8")
            response.getWriter().write(objectMapper.writeValueAsString(SimpleResponse.success(authentication)))
        }else{
            super.onAuthenticationSuccess(request, response, authentication)
        }
    }
}