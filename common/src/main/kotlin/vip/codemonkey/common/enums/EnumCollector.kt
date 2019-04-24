package vip.codemonkey.common.enums

import vip.codemonkey.common.constant.Punctuations
import vip.codemonkey.common.utils.FileUtil
import java.io.IOException
import java.net.JarURLConnection
import java.net.URLDecoder
import java.util.*


/**
 * 枚举收集器
 *@author wang zhengtao
 */
class EnumCollector {

    private val enumViewList = HashSet<EnumView>()
    private val packages = HashSet<String>()

    init {
        initialization()
    }

    private fun initialization() {
        val packageName = EnumCollector::class.java.getPackage().name
        loadEnums(packageName)
    }

    fun loadEnums(packageName: String) {
        if (packages.contains(packageName)) {
            return
        }

        val packageDir = packageName.replace(".", "/")
        try {
            val loader = Thread.currentThread().contextClassLoader
            val resources = loader.getResources(packageDir)
            val classSuffix = ".class"
            while (resources.hasMoreElements()) {
                // 获取下一个元素
                val url = resources.nextElement()
                // 得到协议的名称
                val protocol = url.protocol
                // 如果是以文件的形式保存在服务器上
                if ("file" == protocol) {
                    val filePath = URLDecoder.decode(url.file, "UTF-8")
                    FileUtil.getAllFiles(filePath).stream()
                        .filter { f -> f.name.endsWith(classSuffix) }
                        .forEach { classFile ->
                            val fileName = classFile.name
                            val classPath = packageName + Punctuations.DOT + fileName.substring(
                                0,
                                fileName.lastIndexOf(classSuffix)
                            )
                            convertAndLoad(classPath, loader, enumViewList)

                        }
                }
                // 如果是以jar的形式保存在服务器上
                if ("jar" == protocol) {
                    val jarFile = (url.openConnection() as JarURLConnection).jarFile
                    val entries = jarFile.entries()
                    while (entries.hasMoreElements()) {
                        val entry = entries.nextElement()
                        var name = entry.name
                        if (name[0] == '/') {
                            name = name.substring(1)
                        }
                        if (name.endsWith(classSuffix)) {
                            val classPath = name.substring(0, name.lastIndexOf(classSuffix))
                            convertAndLoad(classPath, loader, enumViewList)
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        packages.add(packageName)
    }

    fun getEnumViewList(): Set<EnumView> {
        return enumViewList
    }

    private fun convertAndLoad(classPath: String, loader: ClassLoader, views: MutableSet<EnumView>) {
        try {
            val tmpClass = loader.loadClass(classPath)
            val interfaces = Arrays.asList(*tmpClass.interfaces)
            if (interfaces.contains(BaseEnum::class.java) && tmpClass.isEnum) {
                views.add(EnumConverter.convert(tmpClass as Class<out BaseEnum>))
            }
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

    }
}