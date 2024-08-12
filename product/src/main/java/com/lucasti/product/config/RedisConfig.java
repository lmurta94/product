package com.lucasti.product.config;


import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))  // Configura a expiração padrão para 10 minutos
                .disableCachingNullValues();

        RedisCacheConfiguration userCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30)); // Configura a expiração específica para o cache "userCache"

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultCacheConfig)
                .withCacheConfiguration("userCache", userCacheConfig) // Nome do cache e sua configuração
                .build();
    }


    //OPCAO 2
//    @Bean
//    public RedisCacheManagerBuilderCustomizer myRedisCacheManagerBuilderCustomizer() {
//        return (builder) -> builder
//                .withCacheConfiguration("cache1", RedisCacheConfiguration
//                        .defaultCacheConfig().entryTtl(Duration.ofSeconds(10)))
//                .withCacheConfiguration("cache2", RedisCacheConfiguration
//                        .defaultCacheConfig().entryTtl(Duration.ofMinutes(1)));
//
//    }
}
