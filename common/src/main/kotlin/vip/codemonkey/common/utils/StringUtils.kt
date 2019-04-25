package vip.codemonkey.common.utils

import vip.codemonkey.common.constant.Punctuations

class StringUtils {
    companion object{
        fun list2String(attribute: List<String>,separator:String) = attribute.joinToString(separator)

        fun string2List(attribute:String? ,separator:String) = if (attribute.isNullOrBlank()) mutableListOf() else attribute.split(separator).toMutableList()
    }
}