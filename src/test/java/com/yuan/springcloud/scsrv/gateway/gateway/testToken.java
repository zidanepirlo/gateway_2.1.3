package com.yuan.springcloud.scsrv.gateway.gateway;

import com.yuan.springcloud.scsrv.gateway.Application;
import com.yuan.springcloud.scsrv.gateway.common.JwtUtils;
import com.yuan.springcloud.scsrv.gateway.filter.BuildTokenGatewayFilterFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static com.yuan.springcloud.scsrv.gateway.common.JwtUtils.TOKEN_ALGORITHM_HS256;

/**
 * testToken
 *
 * @author yuanqing
 * @create 2019-06-11 22:34
 **/
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes={Application.class})
public class testToken {

    private static final Logger logger = LoggerFactory.getLogger(testToken.class);

    @Test
    public void createToken() throws Exception {

       String token = JwtUtils.createToken("123456", Calendar.MINUTE,30,"abcd",TOKEN_ALGORITHM_HS256);
       System.out.println(token);
    }

    @Test
    public void analysisToken() throws Exception {

        String userId = "123456";
        String token = JwtUtils.createToken(userId, Calendar.MINUTE,30,"abcd",TOKEN_ALGORITHM_HS256);
        logger.info("token={}",token);
        String userIdGet = JwtUtils.analysisTokenByKey(token,"abcd","USER_ID");
        logger.info("userIdGet={}",userIdGet);
    }

    @Test
    public void createTokenByMap() throws Exception {

        Map<String,String> map = new HashMap<>();
        map.put("user_id","123456");
//        map.put("name","yuanqing");

        String token = JwtUtils.createToken(map,Calendar.MINUTE,30,"abcd",TOKEN_ALGORITHM_HS256);
        logger.info("token={}",token);

        List<String> keys = new ArrayList<>();
        for (Map.Entry<String,String> entry : map.entrySet()) {
            keys.add(entry.getKey());
        }

        Map<String,String> result =JwtUtils.analysisTokenByKey(token,"abcd",keys);
        logger.info("result={}",result);

    }

    public static void main(String[] args)throws Exception {
        String token = JwtUtils.createToken("123456", Calendar.MINUTE,30,"abcd",TOKEN_ALGORITHM_HS256);
    }
}
