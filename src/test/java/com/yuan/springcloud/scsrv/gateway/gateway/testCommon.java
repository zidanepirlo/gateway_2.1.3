package com.yuan.springcloud.scsrv.gateway.gateway;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * testCommon
 *
 * @author yuanqing
 * @create 2019-07-12 09:02
 **/
@RunWith(SpringRunner.class)
public class testCommon {

    private static final Logger logger = LoggerFactory.getLogger(testCommon.class);

    @Test
    public void testStringRedisSerializer(){

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        logger.info("stringRedisSerializer={}",stringRedisSerializer.serialize("ReactiveRedis"));
    }
}
