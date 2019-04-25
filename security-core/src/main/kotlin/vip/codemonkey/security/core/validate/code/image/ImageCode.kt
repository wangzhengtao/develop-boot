package vip.codemonkey.security.core.validate.code.image

import java.awt.image.BufferedImage
import java.io.Serializable
import java.time.LocalDateTime

data class ImageCode(var code:String,var image:BufferedImage): Serializable {
    var expireTime:LocalDateTime = LocalDateTime.MIN
    constructor(code: String,image: BufferedImage,expireIn:Long) : this(code,image) {
        expireTime = LocalDateTime.now().plusSeconds(expireIn)
    }

    fun isExpire() = LocalDateTime.now().isAfter(this.expireTime)
}