package cn.rentaotao.core.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 *
 * </p>
 *
 * @author zhonghuashishan
 */
public class RedisCache {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RedisCache(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 缓存存储
     */
    public void set(String key, String value, int seconds){

        ValueOperations<String,String> vo = redisTemplate.opsForValue();
        if(seconds > 0){
            vo.set(key, value, seconds, TimeUnit.SECONDS);
        }else{
            vo.set(key, value);
        }
    }

    /**
     * 缓存获取
     */
    public String get(String key){
        ValueOperations<String,String> vo = redisTemplate.opsForValue();
        return vo.get(key);
    }

    /**
     * 缓存手动失效
     */
    public boolean delete(String key){
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

}
