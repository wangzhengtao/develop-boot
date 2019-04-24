package vip.codemonkey.common.tree;

/**
 * @author wang zhengtao
 */
public class DemoTreeNode extends BaseTreeNode {
    private int id;
    private String field1;
    private String field2;
    private String field3;

    public DemoTreeNode(String code, String parentCode, String label) {
        super(code, parentCode, label);
    }

    public DemoTreeNode(String code, String parentCode, String label, String field1) {
        super(code, parentCode, label);
        this.field1 = field1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }
}
