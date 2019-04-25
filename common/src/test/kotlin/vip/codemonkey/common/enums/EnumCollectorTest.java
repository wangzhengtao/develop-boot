package vip.codemonkey.common.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

/**
 * @author wang zhengtao
 */
public class EnumCollectorTest {

    @Test
    public void test(){
        EnumCollector enumCollector = new EnumCollector();
        Set<EnumView> enumViewList = enumCollector.getEnumViewList();
        Assertions.assertTrue(enumViewList.size() == 2);
    }
}
