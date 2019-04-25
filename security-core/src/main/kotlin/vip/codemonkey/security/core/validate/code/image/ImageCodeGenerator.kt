package vip.codemonkey.security.core.validate.code.image

import org.springframework.stereotype.Component
import java.awt.image.BufferedImage

@Component
class ImageCodeGenerator {

    fun generate():ImageCode{
        return ImageCode("1111", BufferedImage(1,2,3),60)
    }
}