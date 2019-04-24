package vip.codemonkey.common.tree

import java.util.*
import java.util.stream.Collectors
import kotlin.streams.toList


/**
 *@author wang zhengtao
 *
 */
abstract class TreeConverter<N : BaseTreeNode, T : BaseTreeView> {

    @JvmOverloads
    fun composeTree(nodes: List<N>, rootNode: N? = findRoot(nodes)): T? {
        if (Objects.isNull(rootNode)) {
            return null
        }
        val root = nodeToView(rootNode!!)
        nodes.stream().map { nodeToView(it) }.toList()
        val treeViews =
            nodes.stream().map { nodeToView(it) }.toList()
        //key: parentCode  value: List<T extend BaseTreeView>
        val parentMapping = treeViews.asSequence().groupBy { it.parentCode }

        //key: code value: extends BaseTreeView
        val treeViewMapping = treeViews.map{  it.parentCode to it}.toMap().toMutableMap()
        if (Objects.isNull(treeViewMapping[root.code])) {
            treeViewMapping.put(root.code,root)
        }
        parentMapping.forEach { (parentCode, children) ->
            val node = treeViewMapping[parentCode]!!
            children.forEach {  it.level = node.level + 1 }
            node.children.addAll(children)
            node.isLeaf = false
        }

        return root
    }

    /**
     * 查找根节点, 默认 parentCode 未空的是根节点
     * @param nodes
     * @return
     */
    protected fun findRoot(nodes: List<N>): N? {
        val nodeOptional = nodes.stream().filter { Objects.isNull(it.parentCode) }.findFirst()
        return if (nodeOptional.isPresent) nodeOptional.get() else null
    }

    /**
     * node 转 view
     * @param node
     * @return
     */
    protected abstract fun nodeToView(node: N): T

}
