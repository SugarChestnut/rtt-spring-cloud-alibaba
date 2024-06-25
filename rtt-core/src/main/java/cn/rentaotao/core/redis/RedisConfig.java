package cn.rentaotao.core.redis;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * <p>
 *
 * </p>
 *
 * @author zhonghuashishan
 */
@Data
@Configuration
@ConditionalOnClass(RedisConnectionFactory.class)
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

//    @Value("${spring.redis.password}")
//    private String password;
//
//    @Value("${spring.redis.timeout}")
//    private int timeout;

    @Bean
    @ConditionalOnBean(RedisConnectionFactory.class)
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    @ConditionalOnClass(RedissonClient.class)
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://"+host+":"+port)
//                .setPassword(password)
                .setConnectionMinimumIdleSize(10)
                .setConnectionPoolSize(100)
                .setIdleConnectionTimeout(600000)
                .setSubscriptionConnectionMinimumIdleSize(10)
                .setSubscriptionConnectionPoolSize(100);
//                .setTimeout(timeout);

        config.setCodec(new StringCodec());
        config.setThreads(5);
        config.setNettyThreads(5);

        return Redisson.create(config);
    }

    @Bean
    @ConditionalOnBean(RedisConnectionFactory.class)
    public RedisCache redisCache(RedisTemplate<String, String> redisTemplate) {
        return new RedisCache(redisTemplate);
    }

    @Bean
    @ConditionalOnClass(RedissonClient.class)
    public RedisLock redisLock(RedissonClient redissonClient) {
        return new RedisLock(redissonClient);
    }
}
