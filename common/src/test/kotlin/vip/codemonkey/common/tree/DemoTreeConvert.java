package vip.codemonkey.common.tree;

/**
 * @author wang zhengtao
 */
public class DemoTreeConvert extends TreeConverter<DemoTreeNode,DemoTreeView> {
    @Override
    protected DemoTreeView nodeToView(DemoTreeNode node) {
        DemoTreeView treeView = new DemoTreeView();
        treeView.setCode(node.getCode());
        treeView.setParentCode(node.getParentCode());
        treeView.setLabel(node.getLabel());
        treeView.setField1(node.getField1());
        return treeView;
    }
}
