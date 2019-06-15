package com.yuan.springcloud.scsrv.gateway.service.Impl;

import com.yuan.springcloud.scsrv.gateway.service.ItokenService;
import com.yuan.springcloud.scsrv.gateway.utils.JedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ItokenServiceImpl
 *
 * @author yuanqing
 * @create 2019-04-10 18:53
 **/
//@Service
public class TokenServiceImpl implements ItokenService {

    @Autowired
    private JedisManager jedisManager;



}
