package com.yuan.springcloud.scsrv.gateway.RateLimiter;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


//@Component
public class RequestRateLimiterCustom {

//    @Bean(name = "remoteAddrKeyResolver")
    public RemoteAddrKeyResolver remoteAddrKeyResolver() {
        return new RemoteAddrKeyResolver();
    }
}