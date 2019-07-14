package com.yuan.springcloud.scsrv.gateway.utils;

import io.lettuce.core.RedisURI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

/**
 * LettuceUtil
 *
 * @author yuanqing
 * @create 2019-03-06 08:33
 **/
//@Configuration
//@EnableConfigurationProperties(LettuceRedisProperties.class)
public class LettuceUtil {

    private static final Logger logger = LoggerFactory.getLogger(LettuceUtil.class);

//    @Autowired
    private LettuceRedisProperties lettuceRedisProperties;

    private ArrayList<RedisURI> redisUriList = new ArrayList<>();

//    @Bean
    public ArrayList<RedisURI> initRedisUriList(){

      String redisNodes = lettuceRedisProperties.getClusterNodes();
      String[] redisNodesArray = redisNodes.split(",");
      for (int i=0;i<redisNodesArray.length;i++){
          redisUriList.add(RedisURI.create(redisNodesArray[i]));
      }
      return redisUriList;
    }

    public ArrayList<RedisURI> getRedisUriList() {
        return redisUriList;
    }

}
