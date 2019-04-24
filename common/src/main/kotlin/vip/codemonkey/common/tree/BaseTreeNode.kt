package vip.codemonkey.common.tree

import java.io.Serializable


/**
 *@author wang zhengtao
 *
 */
open class BaseTreeNode(
    open var code: String,
    open var parentCode: String,
    open var label: String
): Serializable