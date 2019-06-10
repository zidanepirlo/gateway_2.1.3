package com.yuan.springcloud.scsrv.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.yuan.springcloud.scsrv.gateway.common.JwtUtils;
import com.yuan.springcloud.scsrv.gateway.entity.JwtEntity;
import com.yuan.springcloud.scsrv.gateway.entity.TokenOpeResult;
import com.yuan.springcloud.scsrv.gateway.enums.AuthStatus;
import com.yuan.springcloud.scsrv.gateway.enums.Duration;
import com.yuan.springcloud.scsrv.gateway.enums.TokenRefresh;
import com.yuan.springcloud.scsrv.gateway.enums.TokenType;
import com.yuan.springcloud.scsrv.gateway.utils.HttpUtils;
import com.yuan.springcloud.scsrv.gateway.utils.JedisManager;
import com.yuan.springcloud.scsrv.gateway.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;


@Component("ClearTokenGatewayFilterFactory")
public class ClearTokenGatewayFilterFactory extends AbstractGatewayFilterFactory<ClearTokenGatewayFilterFactory.Config> {

    private static final Logger logger = LoggerFactory.getLogger(ClearTokenGatewayFilterFactory.class);

    @Autowired
    private JedisManager jedisManager;

    public ClearTokenGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("clearTokenBaseFieldName");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            try {

                String accessToken = HttpUtils.parseHeaderToken(exchange.getRequest().getHeaders(), TokenType.ACCESS_TOKEN.toString());
                String refreshToken = HttpUtils.parseHeaderToken(exchange.getRequest().getHeaders(), TokenType.REFRESH_TOKEN.toString());
                String clearTokenBaseField = HttpUtils.parseQueryParams(exchange.getRequest(), config.getClearTokenBaseFieldName());

                if (!StringUtils.isEmpty(clearTokenBaseField)) {

                    if (!StringUtils.isEmpty(accessToken) && !StringUtils.isEmpty(refreshToken)) {

                        jedisManager.delValue(accessToken);
                        jedisManager.delValue(refreshToken);
                        jedisManager.delValue(clearTokenBaseField);
                    }
                    else {

                        String tokenStoreJson = jedisManager.getValueByKey(clearTokenBaseField, 3);
                        JwtEntity jwtEntity = JSON.parseObject(tokenStoreJson, new TypeReference<JwtEntity>() {
                        });
                        jedisManager.delValue(jwtEntity.getAccessToken());
                        jedisManager.delValue(jwtEntity.getRefreshToken());
                        jedisManager.delValue(clearTokenBaseField);
                    }
                } else {
                    logger.error("ClearTokenGatewayFilterFactory clearTokenBaseField is null!");
                }

            } catch (Throwable throwable) {
                logger.error(throwable.getMessage(), throwable);
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {

        public String getClearTokenBaseFieldName() {
            return clearTokenBaseFieldName;
        }

        public void setClearTokenBaseFieldName(String clearTokenBaseFieldName) {
            this.clearTokenBaseFieldName = clearTokenBaseFieldName;
        }

        @NotEmpty
        private String clearTokenBaseFieldName;

    }
}
