package vip.codemonkey.security.core.validate.code

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import vip.codemonkey.security.core.properties.SecurityProperties
import vip.codemonkey.security.core.validate.code.image.ImageCodeGenerator
import vip.codemonkey.security.core.validate.code.sms.DefaultSmsCodeSender
import vip.codemonkey.security.core.validate.code.sms.SmsCodeSender

@Configuration
class ValidateCodeBeanConfiguration {

    @Autowired
    lateinit var securityProperties:SecurityProperties

    @Bean("imageCodeGenerator")
    @ConditionalOnMissingBean(name = arrayOf("imageCodeGenerator"))
    fun imageCodeGeneratorImpl():ValidateCodeGenerator{
        return ImageCodeGenerator(securityProperties)
    }

    @Bean
    @ConditionalOnMissingBean(SmsCodeSender::class)
    fun smsCodeSender():SmsCodeSender{
        return DefaultSmsCodeSender()
    }
}