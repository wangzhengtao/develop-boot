package vip.codemonkey.common.enums

/**
 * @author wang zhengtao
 */
interface BaseEnum {
    fun getValue(): String
    fun getLabel(): String
    fun getDescription(): String
}