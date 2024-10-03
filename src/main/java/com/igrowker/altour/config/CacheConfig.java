package com.igrowker.altour.config;

import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
@EnableCaching
public class CacheConfig {

	@Bean
	CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);

		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofMinutes(10)) // Tiempo de vida de los elementos en caché
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer));

		return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(redisCacheConfiguration).build();
	}
}
