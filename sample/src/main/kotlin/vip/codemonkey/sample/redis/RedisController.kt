package vip.codemonkey.sample.redis

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.bind.annotation.*
import vip.codemonkey.data.redis.config.DistributedLock
import vip.codemonkey.common.dto.SimpleResponse
import java.util.*

@RestController
@RequestMapping("redis")
class RedisController {

    protected val logger: Logger by lazy { LoggerFactory.getLogger(this::class.java) }

    @Autowired
    lateinit var template: RedisTemplate<Any,Any>
    @Autowired
    lateinit var lock: DistributedLock

    @PostMapping("put/{key}")
    fun put(@RequestBody obj:Any,@PathVariable("key") key:String): SimpleResponse<Any> {
        val ops = template.opsForValue()
        ops.set(key,obj)

        return SimpleResponse.success("提交成功")
    }

    @GetMapping("{key}")
    fun get(@PathVariable key:String): SimpleResponse<Any> {
        val ops = template.opsForValue()
        val value = ops.get(key);
        if(value != null){
            return SimpleResponse.success(value)
        }
        return SimpleResponse.failed("查询的值不存在");
    }

    @GetMapping("block/{key}")
    fun block(@PathVariable key:String): SimpleResponse<Any> {
        logger.info("request in key is {}",key)
        var identifier = UUID.randomUUID().toString()
        lock.lockWithTimeout(key, identifier)
        logger.info(
            "book course get lock success lock key is {} identifier is {}",
            key,
            identifier
        )
        Thread.sleep(1000 * 20)
        logger.info("老子休息了 20 秒")
        lock.releaseLock(key,identifier)
        return SimpleResponse.success("请求成功")
    }
}