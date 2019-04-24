package vip.codemonkey.sample;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * @author wang zhengtao
 */
public class SampleTest {

    @Test
    public void test(){

        long query = LocalDateTime.of(2019, 04, 9, 15, 12).toInstant(OffsetDateTime.now().getOffset()).toEpochMilli();
        long server = LocalDateTime.of(2019, 04, 9, 15, 13).toInstant(OffsetDateTime.now().getOffset()).toEpochMilli();
    }
}
