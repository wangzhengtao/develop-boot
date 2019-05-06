package vip.codemonkey.data.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class RedisConfig {
    //各 Redis 节点信息
    @Value("${spring.redis.password}")
    private String password;
    //各 Redis 节点信息
    @Value("${spring.redis.cluster.nodes}")
    private String nodes;
    //执行命令超时时间
    @Value("${spring.redis.cluster.commandTimeout}")
    private String commandTimeout;
    //重试次数
    @Value("${spring.redis.cluster.maxAttempts}")
    private String maxAttempts;
    //跨集群执行命令时要遵循的最大重定向数量
    @Value("${spring.redis.cluster.maxRedirects}")
    private String maxRedirects;
    //连接池最大连接数（使用负值表示没有限制）
    @Value("${spring.redis.cluster.maxActive}")
    private String maxActive;
    //连接池最大阻塞等待时间（使用负值表示没有限制）
    @Value("${spring.redis.cluster.maxWait}")
    private String maxWait;
    //连接池中的最大空闲连接
    @Value("${spring.redis.cluster.maxIdle}")
    private String maxIdle;
    //连接池中的最小空闲连接
    @Value("${spring.redis.cluster.minIdle}")
    private String minIdle;
    //是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
    @Value("${spring.redis.cluster.testOnBorrow}")
    private String testOnBorrow;
    //当调用return Object方法时，是否进行有效性检查
    @Value("${spring.redis.cluster.testOnReturn}")
    private String testOnReturn;
    //Idle时进行连接扫描
    @Value("${spring.redis.cluster.testWhileIdle}")
    private String testWhileIdle;
    //idle object evitor两次扫描之间要sleep的毫秒数
    @Value("${spring.redis.cluster.timeBetweenEvictionRunsMillis}")
    private String timeBetweenEvictionRunsMillis;
    //idle object evitor每次扫描的最多的对象数
    @Value("${spring.redis.cluster.numTestsPerEvictionRun}")
    private String numTestsPerEvictionRun;
    //一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
    @Value("${spring.redis.cluster.minEvictableIdleTimeMillis}")
    private String minEvictableIdleTimeMillis;


    /**
     * 配置 Redis 连接池信息
     */
    @Bean("jedisPoolConfig")
    public JedisPoolConfig getJedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.valueOf(maxActive));
        config.setMaxIdle(Integer.valueOf(maxIdle));
        config.setMinIdle(Integer.valueOf(minIdle));//设置最小空闲数
        config.setMaxWaitMillis(Integer.valueOf(maxWait));
        config.setTestOnBorrow(Boolean.valueOf(testOnBorrow));
        config.setTestOnReturn(Boolean.valueOf(testOnReturn));
        //Idle时进行连接扫描
        config.setTestWhileIdle(Boolean.valueOf(testWhileIdle));
        //表示idle object evitor两次扫描之间要sleep的毫秒数
        config.setTimeBetweenEvictionRunsMillis(Long.valueOf(timeBetweenEvictionRunsMillis));
        //表示idle object evitor每次扫描的最多的对象数
        config.setNumTestsPerEvictionRun(Integer.valueOf(numTestsPerEvictionRun));
        //表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
        config.setMinEvictableIdleTimeMillis(Long.valueOf(minEvictableIdleTimeMillis));
        return config;
    }

    /**
     * 配置 Redis Cluster 信息
     */
    @Bean("redisClusterConfiguration")
    public RedisClusterConfiguration getJedisCluster() {
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        redisClusterConfiguration.setMaxRedirects(Integer.valueOf(maxRedirects));
        List<RedisNode> nodeList = new ArrayList<>();
        String[] cNodes = nodes.split(",");
        //分割出集群节点
        for (String node : cNodes) {
            String[] hp = node.split(":");
            nodeList.add(new RedisNode(hp[0], Integer.parseInt(hp[1])));
        }
        redisClusterConfiguration.setClusterNodes(nodeList);

        return redisClusterConfiguration;
    }

    /**
     * 配置 Redis 连接工厂
     */
    @Bean("jeidsConnectionFactory")
    public JedisConnectionFactory getJedisConnectionFactory(RedisClusterConfiguration redisClusterConfiguration, JedisPoolConfig jedisPoolConfig) {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration, jedisPoolConfig);
        jedisConnectionFactory.setPassword(password);
        return jedisConnectionFactory;
    }



    /**
     * 提供集群部署的情况下面的redisTemplate支持
     * @return
     */
    @Bean(name = "redisTemplate",autowireCandidate = false)
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory jeidsConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
        redisTemplate.setConnectionFactory(jeidsConnectionFactory);
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(om);
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.afterPropertiesSet();
        //redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }

    @Bean("lettuceConnectionFactory")
    LettuceConnectionFactory redisConnectionFactory() {
        org.springframework.boot.autoconfigure.data.redis.RedisProperties.Cluster clusterProperties = new RedisProperties.Cluster();
        clusterProperties.setNodes(Arrays.asList(nodes.split(",")));
        RedisClusterConfiguration config = new RedisClusterConfiguration(
                clusterProperties.getNodes());

        config.setPassword(password);
        if (clusterProperties.getMaxRedirects() != null) {
            config.setMaxRedirects(clusterProperties.getMaxRedirects());
        }

        return new LettuceConnectionFactory(config);
    }

    /**
     * 提供集群部署的情况下面的redisTemplate支持
     * @return
     */
    @Bean(name = "lettcueRdisTemplate")
    @Primary
    public RedisTemplate<Object, Object> lettcuerRdisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(om);
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.afterPropertiesSet();
        //redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }


}
