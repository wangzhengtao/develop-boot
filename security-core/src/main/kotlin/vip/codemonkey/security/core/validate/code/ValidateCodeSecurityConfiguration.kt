package vip.codemonkey.security.core.validate.code

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import org.springframework.stereotype.Component

@Component("validateCodeSecurityConfiguration")
class ValidateCodeSecurityConfiguration: SecurityConfigurerAdapter<DefaultSecurityFilterChain,HttpSecurity>() {

    @Autowired
    lateinit var validateCodeFilter: ValidateCodeFilter

    override fun configure(builder: HttpSecurity) {
        builder.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}