package com.yuan.springcloud.scsrv.gateway.RateLimiter;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * RateLimiter
 *
 * @author yuanqing
 * @create 2019-07-10 09:34
 **/

@Component
public class RateLimiter {

    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }
}
