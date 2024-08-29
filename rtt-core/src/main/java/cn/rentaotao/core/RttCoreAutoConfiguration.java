package cn.rentaotao.core;

import cn.rentaotao.core.redis.RedisConfig;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import({WebConfig.class, RedisConfig.class, MybatisPlusConfig.class})
public class RttCoreAutoConfiguration {
}
