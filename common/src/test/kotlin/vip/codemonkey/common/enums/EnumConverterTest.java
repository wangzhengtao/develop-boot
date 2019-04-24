package vip.codemonkey.common.enums;

import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;

/**
 * @author wang zhengtao
 */
public class EnumConverterTest {

    @Test
    public void test(){
        EnumView convert = EnumConverter.convert(Status.class);
        Assert.assertTrue(Objects.equals(convert.getDescription(),Status.DISABLE.getDescription()));
    }
}
