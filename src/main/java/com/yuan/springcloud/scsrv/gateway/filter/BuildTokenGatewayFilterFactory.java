package com.yuan.springcloud.scsrv.gateway.filter;

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
import io.netty.handler.codec.http.HttpUtil;
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


@Component("PreTokenProcessGatewayFilterFactory")
public class BuildTokenGatewayFilterFactory extends AbstractGatewayFilterFactory<BuildTokenGatewayFilterFactory.Config> {

    private static final Logger logger = LoggerFactory.getLogger(BuildTokenGatewayFilterFactory.class);

    @Autowired
    private JedisManager jedisManager;

    public BuildTokenGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("buildTokenBaseFieldName");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

           return tokenStore(exchange,chain,config);
//            return chain.filter(exchange);
        };
    }

    private Mono<Void> tokenStore(ServerWebExchange exchange,GatewayFilterChain chain, Config config){

        try{

            String buildTokenBaseFieldValue = HttpUtils.parseQueryParams(exchange.getRequest(),config.getBuildTokenBaseFieldName());
            if (StringUtils.isEmpty(buildTokenBaseFieldValue)){
                TokenOpeResult tokenOpeResult= new TokenOpeResult(AuthStatus.AUTH_FAIL.toString(),
                        TokenRefresh.TOKEN_BUILD_FAIL.toString()+ ": "+config.getBuildTokenBaseFieldName() +" not existed!");
                DataBuffer buffer = HttpUtils.buildTokenRespDate(tokenOpeResult,exchange.getResponse());
                return exchange.getResponse().writeWith(Mono.just(buffer));
            }
            //get token
            String accessToken = JwtUtils.createAccessToken(buildTokenBaseFieldValue);
            String refreshToken = JwtUtils.createRefreshToken(buildTokenBaseFieldValue);

            //store token to ThreadLocal
            JwtEntity jwtEntity = new JwtEntity(accessToken, refreshToken,buildTokenBaseFieldValue);
            logger.info("BuildTokenGatewayFilterFactory_jwtEntity={}", jwtEntity);

            //store token to redis, 3 days in redis
            String jsonJwtEntity = JsonUtil.toJSONString(jwtEntity);

            //accessToken-> {accessToken, refreshToken, buildTokenFieldValue}
            jedisManager.putValue(accessToken,jsonJwtEntity,3, Duration.ONE_DAY.getMilliSeconds()*10);
            //refreshToken-> {refreshToken}
            jedisManager.putValue(refreshToken,refreshToken,3, Duration.ONE_DAY.getMilliSeconds()*10);
            //buildTokenFieldValue-> {accessToken, refreshToken, buildTokenFieldValue}
            jedisManager.delValue(buildTokenBaseFieldValue);
            jedisManager.putValue(buildTokenBaseFieldValue,jsonJwtEntity,3, Duration.ONE_DAY.getMilliSeconds()*10);

            if (logger.isDebugEnabled()){
                logger.info("BuildTokenGatewayFilterFactory_redis_redis_store_accessToken={}", jwtEntity);
                logger.info("BuildTokenGatewayFilterFactory_redis_redis_store_refreshToken={}", refreshToken);
                logger.info("BuildTokenGatewayFilterFactory_get_from_redis_accessToken={}",jedisManager.getValueByKey(accessToken,3));
                logger.info("BuildTokenGatewayFilterFactory_get_from_redis_refreshToken={}",jedisManager.getValueByKey(refreshToken,3));
                logger.info("BuildTokenGatewayFilterFactory_get_from_redis_buildTokenBaseFieldValue={}",jedisManager.getValueByKey(buildTokenBaseFieldValue,3));
            }

            //store token to response http header,return for app store
            HttpUtils.saveRespHeaderToken(exchange.getResponse(),TokenType.ACCESS_TOKEN,accessToken);
            HttpUtils.saveRespHeaderToken(exchange.getResponse(),TokenType.REFRESH_TOKEN,refreshToken);

        }catch (Throwable throwable){
            logger.error(throwable.getMessage(),throwable);
            TokenOpeResult tokenOpeResult= new TokenOpeResult(AuthStatus.AUTH_FAIL.toString(),TokenRefresh.TOKEN_BUILD_FAIL.toString());
            DataBuffer buffer = HttpUtils.buildTokenRespDate(tokenOpeResult,exchange.getResponse());
            return exchange.getResponse().writeWith(Mono.just(buffer));
        }

        return chain.filter(exchange);
    }


    public static class Config {

        public String getBuildTokenBaseFieldName() {
            return buildTokenBaseFieldName;
        }

        public void setBuildTokenBaseFieldName(String buildTokenBaseFieldName) {
            this.buildTokenBaseFieldName = buildTokenBaseFieldName;
        }

        @NotEmpty
        private String buildTokenBaseFieldName;

    }
}
