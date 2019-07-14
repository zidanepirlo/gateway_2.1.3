package com.yuan.springcloud.scsrv.gateway.gateway;

import com.yuan.springcloud.scsrv.gateway.Application;
import com.yuan.springcloud.scsrv.gateway.enums.Duration;
import com.yuan.springcloud.scsrv.gateway.utils.JedisManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * testRedis
 *
 * @author yuanqing
 * @create 2019-06-11 21:46
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class testRedis {

    private static final Logger logger = LoggerFactory.getLogger(testRedis.class);

    @Autowired
    private JedisManager jedisManager;

    @Autowired
    private ReactiveRedisTemplate reactiveRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testPutVal(){

        jedisManager.putValue("1234567","1234567",3,
                Duration.getDuration(Duration.ONE_HOUR.toString()).getMilliSeconds()*Integer.valueOf(1));
//        jedisManager.putValue("123456","123456",3, 10000);
    }

    @Test
    public void testReactiveRedisTemplate(){

        DefaultRedisScript redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(
                new ClassPathResource("request_limiter.lua")));
        redisScript.setResultType(List.class);

        List<String> keys = new ArrayList<>();
        keys.add("request_rate_limiter.{localhost}.tokens");
        keys.add("request_rate_limiter.{localhost}.timestamp");

        List<String> scriptArgs = new ArrayList<>();
        scriptArgs.add("10");
        scriptArgs.add("1562836423");
        scriptArgs.add("20");
        scriptArgs.add("1");

//        Flux<List<Long>> flux = this.reactiveRedisTemplate.execute(redisScript, keys, scriptArgs);
//        this.reactiveRedisTemplate.execute(redisScript, keys, scriptArgs).subscribe(System.out::println);


        this.reactiveRedisTemplate.execute(redisScript, keys, scriptArgs).subscribe(o -> {
            logger.info("o={}",o);
        });

//        this.reactiveRedisTemplate.opsForValue().set("yuan111111","qing111111").subscribe(o -> {
//            logger.info("o={}",o);
//        });

    }

    @Test
    public void testRedisLua(){

//        DefaultRedisScript getRedisScript = new DefaultRedisScript<List>();
//        getRedisScript.setResultType(List.class);
//        getRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("request_limiter.lua")));
//
//        List<String> keys = new ArrayList<>();
//        keys.add("request_rate_limiter.{localhost}.tokens");
//        keys.add("request_rate_limiter.{localhost}.timestamp");
//
//        List<String> scriptArgs = new ArrayList<>();
//        scriptArgs.add("10");
//        scriptArgs.add("20");
//        scriptArgs.add("1562836423");
//        scriptArgs.add("1");
//
//        Map<String, Object> argvMap = new HashMap<String, Object>();
//        argvMap.put("10", "10");
//        argvMap.put("20", "20");
//        argvMap.put("1562836423", "1562836423");
//        argvMap.put("1", "1");
//
//
//        redisTemplate.execute(getRedisScript,keys, argvMap);
//
//        String key1 = (String) redisTemplate.opsForValue().get("request_rate_limiter.{localhost}.tokens");
//        logger.info("key1={}",key1);


        DefaultRedisScript getRedisScript = new DefaultRedisScript<List>();
        getRedisScript.setResultType(List.class);
        getRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("test_redis.lua")));

        List<String> keyList = new ArrayList();
        keyList.add("request_rate_limiter.{localhost}.tokens");
        keyList.add("request_rate_limiter.{localhost}.timestamp");
//        keyList.add("{rate.limiting:127.0.0.1}");

        /**
         * 用Mpa设置Lua的ARGV[1]
         */
        Map<String,Object> argvMap = new HashMap<String,Object>();
        argvMap.put("expire",10000);
        argvMap.put("times",10);


        List<String> scriptArgs = new ArrayList<>();
        scriptArgs.add("10");
        scriptArgs.add("20");
        scriptArgs.add("1562836423");
        scriptArgs.add("1");

        /**
         * 调用脚本并执行
         */
//        redisTemplate.execute(getRedisScript,keyList, argvMap);

        reactiveRedisTemplate.execute(getRedisScript,keyList, scriptArgs);

    }


    @Test
    public void testRedisTemplate(){

        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        operations.set("xxoo", "yuanqing");
        logger.info("--------");

        String result = operations.get("xxoo");
        logger.info("result={}",result);

    }

    @Test
    public void test5() throws Exception {

        List<String> keys = new ArrayList<String>();
        keys.add("test_key1");
        List<String> args = new ArrayList<String>();
        args.add("hello,key1");
        String LUA = "redis.call('SET', KEYS[1], ARGV[1]); return ARGV[1]";
        //spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本异常，此处拿到原redis的connection执行脚本
        String result = (String)redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                Object nativeConnection = connection.getNativeConnection();
                // 集群模式和单点模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
                // 集群
                if (nativeConnection instanceof JedisCluster) {
                    return (String) ((JedisCluster) nativeConnection).eval(LUA, keys, args);
                }

                // 单点
                else if (nativeConnection instanceof Jedis) {
                    return (String) ((Jedis) nativeConnection).eval(LUA, keys, args);
                }
                return null;
            }
        });
        System.out.println(result);
    }
}
