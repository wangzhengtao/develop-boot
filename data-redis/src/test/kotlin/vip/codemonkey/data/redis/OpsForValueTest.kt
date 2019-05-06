package vip.codemonkey.data.redis

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OpsForValueTest {

    @Autowired
    lateinit var redisTemplate: RedisTemplate<Any,Any>

    @Test
    fun testOpsForValue(){
        val ops = redisTemplate.opsForValue()
        println(ops.setIfAbsent("test123123","1111111111111"))
        println(ops.setIfAbsent("test123123","1111111111111"))
        println(ops.setIfAbsent("test123123","1111111111111"))
        println(ops.setIfAbsent("test123123","1111111111111"))
    }

}