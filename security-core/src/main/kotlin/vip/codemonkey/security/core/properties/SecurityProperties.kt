package vip.codemonkey.security.core.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "codemonkey.security")
open class SecurityProperties {
    var normal:NormalProperties = NormalProperties()
    var validateCode:ValidateCodeProperties = ValidateCodeProperties()
}