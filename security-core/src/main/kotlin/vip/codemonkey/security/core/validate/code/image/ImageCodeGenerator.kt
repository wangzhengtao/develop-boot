package vip.codemonkey.security.core.validate.code.image

import org.springframework.web.bind.ServletRequestUtils
import vip.codemonkey.security.core.properties.SecurityProperties
import vip.codemonkey.security.core.validate.code.ValidateCodeGenerator
import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage
import java.util.*
import javax.servlet.http.HttpServletRequest

class ImageCodeGenerator(
    var securityProperties: SecurityProperties
) :ValidateCodeGenerator {

    override fun generate(request: HttpServletRequest): ImageCode {

        val width = ServletRequestUtils.getIntParameter(request,"width")?: securityProperties.validateCode.image.width
        val height = ServletRequestUtils.getIntParameter(request,"width")?: securityProperties.validateCode.image.height
        val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)

        val g = image.graphics

        val random = Random()

        g.color = getRandColor(200, 250)
        g.fillRect(0, 0, width, height)
        g.font = Font("Times New Roman", Font.ITALIC, 20)
        g.color = getRandColor(160, 200)
        for (i in 0..154) {
            val x = random.nextInt(width)
            val y = random.nextInt(height)
            val xl = random.nextInt(12)
            val yl = random.nextInt(12)
            g.drawLine(x, y, x + xl, y + yl)
        }

        var sRand = ""
        val length = securityProperties.validateCode.image.length
        for (i in 0 until length) {
            val rand = random.nextInt(10).toString()
            sRand += rand
            g.color = Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110))
            g.drawString(rand, 13 * i + 6, 16)
        }

        g.dispose()

        val expiredInSecond = securityProperties.validateCode.image.expiredIn
        return ImageCode(sRand,image, expiredInSecond)
    }

    /**
     * 生成随机背景条纹
     *
     * @param fc
     * @param bc
     * @return
     */
    private fun getRandColor(fc: Int, bc: Int): Color {
        var fc = fc
        var bc = bc
        val random = Random()
        if (fc > 255) {
            fc = 255
        }
        if (bc > 255) {
            bc = 255
        }
        val r = fc + random.nextInt(bc - fc)
        val g = fc + random.nextInt(bc - fc)
        val b = fc + random.nextInt(bc - fc)
        return Color(r, g, b)
    }
}