package com.yuan.springcloud.scsrv.gateway.gateway;

import com.yuan.springcloud.scsrv.gateway.Application;
import com.yuan.springcloud.scsrv.gateway.enums.Duration;
import com.yuan.springcloud.scsrv.gateway.utils.JedisManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * testRedis
 *
 * @author yuanqing
 * @create 2019-06-11 21:46
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class testRedis {

    @Autowired
    private JedisManager jedisManager;

    @Test
    public void testPutVal(){

        jedisManager.putValue("123456","123456",3,
                Duration.getDuration(Duration.ONE_HOUR.toString()).getMilliSeconds()*Integer.valueOf(1));
//        jedisManager.putValue("123456","123456",3, 10000);
    }

}
