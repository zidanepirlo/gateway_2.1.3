package com.yuan.springcloud.scsrv.gateway.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.net.UnknownHostException;
/**
 * RedisConfig
 *
 * @author yuanqing
 * @create 2019-07-11 11:10
 **/
@Configuration
public class RedisConfig {


//    @Bean (name = "reactiveRedisTemplate")
//    @ConditionalOnBean(ReactiveRedisConnectionFactory.class)
//    public ReactiveRedisTemplate<String, String> reactiveRedisTemplate(
//            ReactiveRedisConnectionFactory reactiveRedisConnectionFactory,
//            ResourceLoader resourceLoader) {
//
//        StringRedisSerializer redisSerializer = new StringRedisSerializer();
//
////        JdkSerializationRedisSerializer redisSerializer = new JdkSerializationRedisSerializer();
//
//        RedisSerializationContext<String, String> serializationContext = RedisSerializationContext.<String, String>newSerializationContext()
//                .key(redisSerializer).value(redisSerializer)
//                .hashKey(redisSerializer).hashValue(redisSerializer).build();
//
//        return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory,
//                serializationContext);
//    }


//    @Bean
//    @ConditionalOnMissingBean(name="redisTemplate")
//    public RedisTemplate<String, String> redisTemplate(
//            LettuceConnectionFactory redisConnectionFactory)
//            throws Exception{
//        RedisTemplate<String, String> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactory);
////        template.setKeySerializer(new StringRedisSerializer());
////        template.setValueSerializer(new StringRedisSerializer());
//        return template;
//    }

//    @Bean
//    @ConditionalOnMissingBean(name="redisTemplate")
//    public RedisTemplate<Object, Object> redisTemplate(
//            RedisConnectionFactory redisConnectionFactory)
//            throws Exception{
//        RedisTemplate<Object, Object> template = new RedisTemplate<Object,Object>();
//        template.setConnectionFactory(redisConnectionFactory);
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(new StringRedisSerializer());
//        return template;
//    }

//    @Bean
//    public ReactiveRedisTemplate<String, String> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
//        ReactiveRedisTemplate<String, String> reactiveRedisTemplate = new ReactiveRedisTemplate<>(factory, RedisSerializationContext.string());
//        return reactiveRedisTemplate;
//    }

//    @Bean
//    @ConditionalOnMissingBean(name="redisTemplate")
//    public RedisTemplate<String, String> redisTemplate(
//            RedisConnectionFactory redisConnectionFactory)
//            throws Exception{
//        RedisTemplate<String, String> template = new RedisTemplate<String,String>();
//        template.setConnectionFactory(redisConnectionFactory);
//        return template;
//    }

//    @Bean
//    @ConditionalOnMissingBean(StringRedisTemplate.class)
//    public StringRedisTemplate stringRedisTemplate(
//            RedisConnectionFactory redisConnectionFactory)
//            throws Exception {
//        StringRedisTemplate template = new StringRedisTemplate();
//        template.setConnectionFactory(redisConnectionFactory);
//        return template;
//    }
}