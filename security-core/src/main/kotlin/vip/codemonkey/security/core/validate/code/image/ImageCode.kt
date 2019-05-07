package vip.codemonkey.security.core.validate.code.image

import vip.codemonkey.security.core.validate.code.ValidateCode
import java.awt.image.BufferedImage
import java.time.LocalDateTime

open class ImageCode(override var code:String,
                    var image:BufferedImage,
                     expireIn:Long
): ValidateCode(code,expireIn = expireIn){
    override var expireTime:LocalDateTime = LocalDateTime.MIN

}