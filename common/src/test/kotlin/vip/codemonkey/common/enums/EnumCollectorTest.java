package vip.codemonkey.common.enums;

import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * @author wang zhengtao
 */
public class EnumCollectorTest {

    @Test
    public void test(){
        EnumCollector enumCollector = new EnumCollector();
        Set<EnumView> enumViewList = enumCollector.getEnumViewList();
        Assert.assertTrue(enumViewList.size() == 2);
    }
}
