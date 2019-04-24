package vip.codemonkey.common.enums

import java.io.Serializable


/**
 *@author wang zhengtao
 *
 */
data class EnumNode(
    var name: String,
    var value: String,
    var label: String
): Serializable