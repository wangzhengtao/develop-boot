package vip.codemonkey.common.number;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

/**
 * @author wang zhengtao
 */
public class Number36Test {

    Number36 number36 = new Number36();

    @Test
    public void testInt2Number(){
        String zero = number36.int2Number(0);
        Assertions.assertTrue(Objects.equals(zero,"0"));
        String integerMax = number36.int2Number(Integer.MAX_VALUE);
        System.out.println(integerMax);

    }

    @Test
    public void testNumber2Int(){
        Assertions.assertEquals(number36.number2Int("11"),37);
        Assertions.assertEquals(number36.number2Int(number36.int2Number(Integer.MAX_VALUE)),Integer.MAX_VALUE);
    }

    @Test
    public void testAdd(){
        String zero = number36.add("0", "0");
        Assertions.assertEquals(zero,"0");
        String one = number36.add("0","1");
        Assertions.assertEquals(one,"1");
        String re36 = number36.add("1","z");
        Assertions.assertEquals(re36,"10");
        String re10 = number36.add("10","1");
        Assertions.assertEquals(re10,"11");
        String re100 = number36.add("zz","1");
        Assertions.assertEquals(re100,"100");
    }

    @Test
    public void testAddInt(){
        String zero = number36.add("0", 0);
        Assertions.assertEquals(zero,"0");
        String one = number36.add("0",1);
        Assertions.assertEquals(one,"1");
        String re36 = number36.add("1",35);
        Assertions.assertEquals(re36,"10");
        String re10 = number36.add("10",1);
        Assertions.assertEquals(re10,"11");
        String re100 = number36.add("1",1295);
        Assertions.assertEquals(re100,"100");
    }

    @Test
    public void testGetInitNumber(){
        System.out.println(number36.getInitNumber(10));
    }

    @Test
    public void testNextNumber(){
        Assertions.assertEquals(number36.nextNumber("000"),"001");
        Assertions.assertEquals(number36.nextNumber("00z"),"010");
    }
}
