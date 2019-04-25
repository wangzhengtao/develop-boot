package vip.codemonkey.security.normal

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import vip.codemonkey.security.core.constant.SecurityConstants
import vip.codemonkey.security.core.validate.code.ValidateCodeSecurityConfiguration

@Configuration
class NormalSecurityConfiguration :WebSecurityConfigurerAdapter() {
    @Autowired
    lateinit var customAuthenticationSuccessHandler: AuthenticationSuccessHandler
    @Autowired
    lateinit var customAuthenticationFailureHandler: AuthenticationFailureHandler
    @Autowired
    lateinit var validateCodeSecurityConfiguration: ValidateCodeSecurityConfiguration

    override fun configure(http: HttpSecurity) {
        http.apply(validateCodeSecurityConfiguration)
            .and()
            .formLogin().loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL).loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                        .successHandler(customAuthenticationSuccessHandler).failureHandler(customAuthenticationFailureHandler)
            .and()
            .authorizeRequests()
            .antMatchers(
                SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM,
                SecurityConstants.DEFAULT_LOGIN_PAGE_URL,
                SecurityConstants.GET_VALIDATE_CODE_URL+"/*",
                "/organization/*"
            ).permitAll().anyRequest().authenticated()
            .and()
            .csrf().disable()
    }
}