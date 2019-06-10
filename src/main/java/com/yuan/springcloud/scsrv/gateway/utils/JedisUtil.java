package com.yuan.springcloud.scsrv.gateway.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PreDestroy;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class JedisUtil {

    private static final Logger logger = LoggerFactory.getLogger(JedisUtil.class);

    @Autowired
    private RedisProperties redisProperties;

    private JedisCluster jedisCluster;

    @Bean
    public JedisCluster initRedisSource() {

        logger.info("init JedisCluster");
        String[] serverArray = redisProperties.getClusterNodes().split(",");
        Set<HostAndPort> nodes = new HashSet<>();

        for (String ipPort : serverArray) {

            String[] ipPortPair = ipPort.split(":");
            String ip = ipPortPair[0].trim();
            String port = ipPortPair[1].trim();
            nodes.add(new HostAndPort(ip, Integer.valueOf(port)));
        }

        jedisCluster = new JedisCluster(nodes, Integer.valueOf(redisProperties.getTimeout().trim())
                , Integer.valueOf(redisProperties.getMaxRedirections().trim()), getPoolConfig());
        return jedisCluster;
    }

    @Bean
    public JedisPoolConfig getPoolConfig() {

        logger.info("init JedisPoolConfig");
        JedisPoolConfig poolConfig = new JedisPoolConfig();

        poolConfig.setMaxTotal(Integer.parseInt(redisProperties.getMaxTotal().trim()));
        poolConfig.setMinIdle(Integer.parseInt(redisProperties.getMinIdle().trim()));
        poolConfig.setMaxWaitMillis(Long.parseLong(redisProperties.getMaxWait().trim()));
        poolConfig.setMaxIdle(Integer.parseInt(redisProperties.getMaxIdle().trim()));
        poolConfig.setTestOnBorrow(redisProperties.getTestOnBorrow());
        poolConfig.setTestOnReturn(redisProperties.getTestOnReturn());
        poolConfig.setTestWhileIdle(redisProperties.getTestWhileIdle());
        poolConfig.setMinEvictableIdleTimeMillis(Long.parseLong(redisProperties.getMinEvictableIdleTimeMillis().trim()));
        poolConfig.setNumTestsPerEvictionRun(Integer.parseInt(redisProperties.getNumTestsPerEvictionRun().trim()));
        poolConfig.setSoftMinEvictableIdleTimeMillis(Long.parseLong(redisProperties.getSoftMinEvictableIdleTimeMillis().trim()));
        poolConfig.setTimeBetweenEvictionRunsMillis(Long.parseLong(redisProperties.getTimeBetweenEvictionRunsMillis().trim()));

        logger.info("clusterNodes : "+redisProperties.getClusterNodes());
        logger.info("serverPort : "+redisProperties.getServerPort());
        logger.info("password : "+redisProperties.getPassword());
        logger.info("maxIdle : "+redisProperties.getMaxIdle());
        logger.info("maxWait : "+redisProperties.getMaxWait());
        logger.info("timeout : "+redisProperties.getTimeout());
        logger.info("maxTotal : "+redisProperties.getMaxTotal());
        logger.info("minIdle : "+redisProperties.getMinIdle());
        logger.info("testOnBorrow : "+redisProperties.getTestOnBorrow());
        logger.info("testOnReturn : "+redisProperties.getTestOnReturn());
        logger.info("testWhileIdle : "+redisProperties.getTestWhileIdle());
        logger.info("maxRedirections : "+redisProperties.getMaxRedirections());
        logger.info("minEvictableIdleTimeMillis : "+redisProperties.getMinEvictableIdleTimeMillis());
        logger.info("numTestsPerEvictionRun : "+redisProperties.getNumTestsPerEvictionRun());
        logger.info("softMinEvictableIdleTimeMillis : "+redisProperties.getSoftMinEvictableIdleTimeMillis());
        logger.info("timeBetweenEvictionRunsMillis : "+redisProperties.getTimeBetweenEvictionRunsMillis());
        logger.info("init JedisPoolConfig end");

        return poolConfig;
    }

    public JedisCluster getRedisSource() {
        return jedisCluster;
    }

    @PreDestroy
    public void closeJedisCluster() {
        logger.info("closeJedisCluster!");
        try {
            if (jedisCluster != null) {
                jedisCluster.close();
                logger.warn("JedisUtil πÿ±’redis¡¨Ω”!");
            }
        } catch (Throwable t) {
            logger.error("closeJedisCluster exception", t);
        }
    }
}
