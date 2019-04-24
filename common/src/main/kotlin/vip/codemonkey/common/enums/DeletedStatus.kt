package vip.codemonkey.common.enums


/**
 *@author wang zhengtao
 *
 */
enum class DeletedStatus(
    private val label: String,
    private val description:String = "删除状态") :BaseEnum {
    DELETED("已删除"), UNDELETED("未删除");

    override fun getValue(): String {
        return this.getValue()
    }

    override fun getLabel(): String {
        return label
    }

    override fun getDescription(): String {
        return description
    }
}