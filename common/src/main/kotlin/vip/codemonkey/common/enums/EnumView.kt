package vip.codemonkey.common.enums

import java.io.Serializable
import java.util.ArrayList


/**
 *@author wang zhengtao
 *
 */
data class EnumView(
    var description: String? = null,
    var nodes:MutableList<EnumNode> = mutableListOf()
) : Serializable