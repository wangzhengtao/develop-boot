package vip.codemonkey.common.tree;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wang zhengtao
 */
public class TreeTest {
    DemoTreeConvert convert;
    List<DemoTreeNode> nodes = new ArrayList<>();
    DemoTreeNode root;
    Gson gson = new GsonBuilder().create();

    @Before
    public void before(){
        convert = new DemoTreeConvert();
        root = new DemoTreeNode("00",null,"root__00","what_00");
        List<DemoTreeNode> level_1 = generateNode(root, 5);
        nodes.addAll(level_1);
        List<DemoTreeNode> level_2 = new ArrayList<>();
        level_1.forEach(it -> level_2.addAll(generateNode(it,5)));
        nodes.addAll(level_2);
        List<DemoTreeNode> level_3 = new ArrayList<>();
        level_2.forEach(it -> level_3.addAll(generateNode(it,5)));
        nodes.addAll(level_3);
    }

    private List<DemoTreeNode> generateNode(DemoTreeNode parent,int size){
        List<DemoTreeNode> result = new ArrayList<>();
        String parentCode = parent.getCode();
        for(int i = 0; i < size; i++){
            String format = String.format("%02d", i);
            String code = parentCode + format;
            String name = "child_"+code;
            String field = "feild"+code;
            result.add(new DemoTreeNode(code,parentCode,name,field));
        }
        return  result;
    }

    @Test
    public void convertTreeTest(){
        DemoTreeView treeView = convert.composeTree(nodes, root);
        System.out.println(gson.toJson(treeView));
    }
}
