package vip.codemonkey.common.enums


/**
 *@author wang zhengtao
 *
 */
enum class Status(private val label: String,
                  private val description: String = "状态") : BaseEnum {
    ENABLED("启用"), DISABLE("禁用");




    override fun getValue(): String {
        return this.name
    }

    override fun getLabel(): String {
        return label
    }

    override fun getDescription(): String {
        return description
    }
}