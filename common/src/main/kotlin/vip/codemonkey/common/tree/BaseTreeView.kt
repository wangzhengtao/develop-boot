package vip.codemonkey.common.tree

import java.io.Serializable
import java.util.ArrayList


/**
 *@author wang zhengtao
 *
 */

open class BaseTreeView(
    var children: MutableList<BaseTreeView> = mutableListOf(),
    var isLeaf: Boolean = true,
    var level:Int = 1,
    override var code: String,
    override var parentCode: String,
    override var label: String
): BaseTreeNode(code, parentCode, label),Serializable