package vip.codemonkey.data.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * redis 锁
 */
@Component
public class DistributedLock {
    private final static String LOCK_KEY = "lock_";
    @Autowired
    @Qualifier("lettcueRdisTemplate")
    RedisTemplate<Object,Object> lettcueRdisTemplate;
    @Autowired
    private Environment env;
    private final String RELEASE_LOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";


    public boolean lockWithTimeout(String key,String identifier){
        String configTimeout = env.getProperty("distribute_lock_timeout");
        String configAcquireTimeOut = env.getProperty("distribute_acquire_timeout");
        long timeout = StringUtils.isEmpty(configTimeout) ? 15 * 1000 : Long.parseLong(configTimeout);
        long acquireTimeout = StringUtils.isEmpty(configAcquireTimeOut) ? 2 * 1000 : Long.parseLong(configAcquireTimeOut);
        return lockWithTimeout(key,identifier,acquireTimeout,timeout);
    }

    /**
     * 加锁
     * @param key
     * @param acquireTimeout 获取锁超时时间， 单位： 毫秒  默认15秒
     * @param timeout 锁超时时间  避免死锁 单位： 毫秒  默认 2 秒
     * @return
     */
    public boolean lockWithTimeout(String key,String identifier, long acquireTimeout,long timeout){
        ValueOperations valueOperations = lettcueRdisTemplate.opsForValue();
        String lockKey = LOCK_KEY+key;




        long end = System.currentTimeMillis() + acquireTimeout;

        while (System.currentTimeMillis() < end){
            Boolean result = valueOperations.setIfAbsent(lockKey, identifier, timeout, TimeUnit.MICROSECONDS);
            if(Objects.nonNull(result) && result){
                return result;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupted();
            }
        }
        return false;
    }

    /**
     * 释放锁
     * @param key
     * @param identifier
     * @return
     */
    public boolean releaseLock(String key,String identifier){
        DefaultRedisScript script = new DefaultRedisScript(RELEASE_LOCK_SCRIPT, Long.class);
        Object result = lettcueRdisTemplate.execute(script, Arrays.asList(key), identifier);
        return Objects.equals(result,-1L);
    }
}
