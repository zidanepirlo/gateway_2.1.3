package com.yuan.springcloud.scsrv.gateway.gateway;

import com.yuan.springcloud.scsrv.gateway.Application;
import com.yuan.springcloud.scsrv.gateway.common.JwtUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;

/**
 * testToken
 *
 * @author yuanqing
 * @create 2019-06-11 22:34
 **/
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes={Application.class})
public class testToken {

    @Test
    public void createToken() throws Exception {

       String token = JwtUtils.createToken("123456", Calendar.MINUTE,30,"abcd");
       System.out.println(token);
    }

    public static void main(String[] args)throws Exception {
        String token = JwtUtils.createToken("123456", Calendar.MINUTE,30,"abcd");
    }
}
