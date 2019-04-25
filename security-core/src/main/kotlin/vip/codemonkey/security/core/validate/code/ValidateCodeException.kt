package vip.codemonkey.security.core.validate.code

import org.springframework.security.core.AuthenticationException

class ValidateCodeException(messages:String): AuthenticationException(messages) {
}