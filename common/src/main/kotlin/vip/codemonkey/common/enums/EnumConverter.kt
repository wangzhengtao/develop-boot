package vip.codemonkey.common.enums

import java.lang.reflect.InvocationTargetException


/**
 * 枚举转换器
 * @author wang zhengtao
 */
class EnumConverter {
    companion object{
        @JvmStatic
        fun convert(en: Class<out BaseEnum>): EnumView {
            if (!en.isEnum) {
                throw IllegalArgumentException("非枚举类型数据,无法转换")
            }
            val result = EnumView()
            try {
                val valuesMethod = en.getDeclaredMethod("values")
                val enums = valuesMethod.invoke(en) as Array<BaseEnum>
                for (anEnum in enums) {
                    if (anEnum is Enum<*>) {
                        result.description = anEnum.getDescription()
                        result.nodes.add(EnumNode((anEnum as Enum<*>).name, anEnum.getValue(), anEnum.getLabel()))
                    }
                }
            } catch (e: NoSuchMethodException) {
                return result
            } catch (e: IllegalAccessException) {
                return result
            } catch (e: InvocationTargetException) {
                return result
            }

            return result
        }
    }

}