package com.yuan.springcloud.scsrv.gateway.service.Impl;

import com.yuan.springcloud.scsrv.gateway.dao.domain.UserLoginDao;
import com.yuan.springcloud.scsrv.gateway.dao.entity.UserLogin;
import com.yuan.springcloud.scsrv.gateway.service.IUserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * UserLoginServiceImp
 *
 * @author yuanqing
 * @create 2019-04-10 08:02
 **/
@Service
public class UserLoginServiceImp implements IUserLoginService {

    @Autowired
    private UserLoginDao userLoginMapper;

    @Override
    public void insertUserLogin(String userId, String userName, String userPasswd) {

        UserLogin userLogin = new UserLogin();
        userLogin.setUserId(userId);
        userLogin.setUserName(userName);
        userLogin.setUserPasswd(userPasswd);
        userLogin.setUserPhone(userId);
        userLogin.setCreateTime(new Date());
        userLogin.setUpdateTime(new Date());
        userLoginMapper.insertSelective(userLogin);
    }

    @Override
    public UserLogin userLogin(String userId, String userPasswd) {
       return userLoginMapper.userLogin(userId,userPasswd);
    }
}
