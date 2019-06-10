package com.yuan.springcloud.scsrv.gateway.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * jedis管理器
 * 
 * @author yuweixiang
 * @version $Id: JedisManager.java, v 0.1 2015-4-15 上午1:15:03 yuweixiang Exp $
 */
@Component("JedisManager")
public class JedisManager implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(JedisManager.class);

    @Autowired
    private JedisUtil jedisUtil;

    /**
     * 把key/data放到cache中。
     * <p>
     * 对象的过期时间将采用默认值.
     * </P>
     * @param key   key值
     * @param data  需要缓存的对象
     * @param tryTimes 尝试次数
     * @param expire   超时时间次数  设置为0 代表不过期
     * @return 是否成功
     */
    public boolean putValue(String key, String data, int tryTimes, long expire){

        int tryTimesTemp = tryTimes;
        // 重试次数
        if (tryTimesTemp < 0) {
            tryTimesTemp = 1;
        }
        if (expire == 0) {
            return   putValue(key,data,tryTimes);
        }

        boolean result = false;
        for (int i = 0; i < tryTimesTemp; i++) {
            try {
                if (!result) {
                    jedisUtil.getRedisSource().set(key, data, "NX", "PX", expire);
                    result = true;
                    break;
                }
            } catch (Exception e) {
                logger.error("数据存入缓存失败!key={},data={},tryTimes={},expire={}", key,
                        data, tryTimes, expire);
            }
        }

        return result;
    }

    public boolean putValue(String key, String data, int tryTimes) {

        int tryTimesTemp = tryTimes;
        // 重试次数
        if (tryTimesTemp < 0) {
            tryTimesTemp = 1;
        }

        boolean result = false;
        for (int i = 0; i < tryTimesTemp; i++) {
            try {
                if (!result) {
                    jedisUtil.getRedisSource().set(key,data);
                    result = true;
                    break;
                }
            } catch (Exception e) {
                logger.error("数据存入缓存失败!key={},data={},tryTimes={}", key, data,
                        tryTimes);
            }
        }

        return result;
    }

    public  boolean delValue(String key){

        boolean result = false;
        try{
            jedisUtil.getRedisSource().del(key);
            result = true;
        }catch (Exception ex){
            logger.error(ex.getMessage(),ex);
        }
        return result;
    }

    public String getValueByKey(String key, int tryTimes) {

        int tryTimesTemp = tryTimes;
        // 重试次数
        if (tryTimesTemp < 0) {
            tryTimesTemp = 1;
        }
        // 尝试多次获取数据
        String data = null;
        for (int i = 0; i < tryTimesTemp; i++) {
            try {
                return jedisUtil.getRedisSource().get(key);
            } catch (Exception e) {
                if (i == tryTimesTemp - 1) {
                    logger.error("[redis获取数据失败.key={}]", key);
                }
            }
        }
        return data;
    }



    public void afterPropertiesSet() throws Exception {

        logger.info("JedisManager init");
    }

}
