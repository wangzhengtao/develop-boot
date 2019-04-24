package vip.codemonkey.common.utils

import java.io.File
import java.util.HashSet


/**
 *@author wang zhengtao
 *
 */
class FileUtil {
    companion object{
        /**
         * 获取目录下所有文件
         */
        @JvmStatic
        fun getAllFiles(dir: String): Set<File> {
            val result = HashSet<File>()
            findFiles(dir, result)
            return result
        }

        @JvmStatic
        fun findFiles(path: String, fileCollection: MutableSet<File>) {
            val dir = File(path)
            if (!dir.exists() || !dir.isDirectory) {
                return
            }
            val files = dir.listFiles()
            for (file in files!!) {
                if (file.isFile) {
                    fileCollection.add(file)
                }
                if (file.isDirectory) {
                    findFiles(file.absolutePath, fileCollection)
                }
            }
        }
    }
}