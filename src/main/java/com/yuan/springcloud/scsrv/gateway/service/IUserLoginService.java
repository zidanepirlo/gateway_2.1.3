package com.yuan.springcloud.scsrv.gateway.service;

import com.yuan.springcloud.scsrv.gateway.dao.entity.UserLogin;

public interface IUserLoginService {

    void insertUserLogin(String userId,String userName,String userPasswd);
    UserLogin userLogin(String userId, String userPasswd);
}
