package vip.codemonkey.security.normal

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository
import vip.codemonkey.security.core.constant.SecurityConstants
import vip.codemonkey.security.core.properties.SecurityProperties
import vip.codemonkey.security.core.validate.code.ValidateCodeSecurityConfiguration
import vip.codemonkey.security.normal.repository.RedisTokenRepositoryImpl

@Configuration
class NormalSecurityConfiguration :WebSecurityConfigurerAdapter() {
    @Autowired
    lateinit var customAuthenticationSuccessHandler: AuthenticationSuccessHandler
    @Autowired
    lateinit var customAuthenticationFailureHandler: AuthenticationFailureHandler
    @Autowired
    lateinit var validateCodeSecurityConfiguration: ValidateCodeSecurityConfiguration
    @Autowired
    lateinit var redisTemplate: RedisTemplate<Any,Any>
    @Autowired
    lateinit var securityProperties: SecurityProperties
    @Autowired
    lateinit var userDetailsService: UserDetailsService
    @Bean
    fun tokenRepository():PersistentTokenRepository{
        return RedisTokenRepositoryImpl(redisTemplate)
    }


    override fun configure(http: HttpSecurity) {
        http.apply(validateCodeSecurityConfiguration)
            .and()
            .formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
                .and()
            .rememberMe()
                .tokenRepository(tokenRepository())
                .tokenValiditySeconds(securityProperties.normal.rememberMeSeconds.toInt())
                .userDetailsService(userDetailsService)
                .and()
            .authorizeRequests()
                .antMatchers(
                    SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                    SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM,
                    SecurityConstants.DEFAULT_LOGIN_PAGE_URL,
                    SecurityConstants.GET_VALIDATE_CODE_URL+"/*"
                )
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
            .csrf().disable()
    }
}