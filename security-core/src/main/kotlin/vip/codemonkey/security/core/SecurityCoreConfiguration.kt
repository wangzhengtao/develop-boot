package vip.codemonkey.security.core

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import vip.codemonkey.security.core.properties.SecurityProperties

@Configuration
@EnableConfigurationProperties(SecurityProperties::class)
class SecurityCoreConfiguration {

    @Bean
    fun passwordEncoder():PasswordEncoder{
        return BCryptPasswordEncoder()
    }

}