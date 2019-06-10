package com.yuan.springcloud.scsrv.gateway.gateway_213;

import com.yuan.springcloud.scsrv.gateway.Application;
import com.yuan.springcloud.scsrv.gateway.common.JwtUtils;
import com.yuan.springcloud.scsrv.gateway.dao.entity.UserLogin;
import com.yuan.springcloud.scsrv.gateway.enums.Duration;
import com.yuan.springcloud.scsrv.gateway.service.IUserLoginService;
import com.yuan.springcloud.scsrv.gateway.utils.JedisManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class testService {

	private static final Logger logger = LoggerFactory.getLogger(testService.class);

	@Autowired
	private IUserLoginService userLoginService;

	@Autowired
	private JedisManager jedisManager;

	@Test
	public void testInsertUserLogin(){

		userLoginService.insertUserLogin("15900709506","yuanqing","111111");
	}

	@Test
	public void testUserLogin(){

		UserLogin userLogin = userLoginService.userLogin("15900709506","111111");
		logger.info("userLogin={}",userLogin);
	}

	@Test
	public void tokenRedisInsert() throws Exception{

//		String user_id = "15900709506";
//		String access_token = JwtUtils.createAccessToken(user_id);
//		logger.info("access_token={}",access_token);
//		boolean result = jedisManager.putValue(access_token,access_token,3, Duration.ONE_DAY.getMilliSeconds()*10);
//		logger.info("result={}",result);

		String user_id = null;
		String access_token = JwtUtils.createAccessToken(user_id);
		logger.info("access_token={}",access_token);
	}

	@Test
	public void tokenRedisGet() throws Exception{

		String access_token = jedisManager.getValueByKey("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJBUFAiLCJpc3MiOiJTZXJ2aWNlIiwiVVNFUl9JRCI6IjE1OTAwNzA5NTA2IiwiZXhwIjoxNTU0OTg0MTY0LCJpYXQiOjE1NTQ5ODA1NjR9.nCaLGgst2Bui_9MjTrpyC0vcJhduJFhCREfg-ghSoxc",3);
		logger.info("access_token={}",access_token);
	}


}
