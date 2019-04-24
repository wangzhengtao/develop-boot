package vip.codemonkey.common.utils

import java.util.regex.Pattern


/**
 *@author wang zhengtao
 *
 */
class CamelStringUtil {
    companion object{
        @JvmStatic
        fun isCamel(str: String): Boolean {
            val regex = "^[a-z]+([A-Z][a-z]+)+$"
            return str.matches(regex.toRegex())
        }

        @JvmStatic
        fun camelStrConvert(camelStr: String, replaceStr: String): String {
            val matcher = Pattern.compile("[A-Z]+[a-z]+").matcher(camelStr)
            val sb = StringBuffer()
            while (matcher.find()) {
                val g = matcher.group()
                matcher.appendReplacement(sb, replaceStr + g.toLowerCase())
            }
            return sb.toString()
        }
    }
}