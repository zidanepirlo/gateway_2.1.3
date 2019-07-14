package com.yuan.springcloud.scsrv.gateway.RateLimiter;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;
/**
 * RemoteAddrKeyResolver
 *
 * @author yuanqing
 * @create 2019-07-13 20:48
 **/
public class RemoteAddrKeyResolver implements KeyResolver {

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }

}