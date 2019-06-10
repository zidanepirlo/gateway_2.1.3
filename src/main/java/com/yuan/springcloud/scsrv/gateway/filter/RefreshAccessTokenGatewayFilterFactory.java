package com.yuan.springcloud.scsrv.gateway.filter;

import com.yuan.springcloud.scsrv.gateway.common.JwtUtils;
import com.yuan.springcloud.scsrv.gateway.entity.JwtEntity;
import com.yuan.springcloud.scsrv.gateway.entity.TokenOpeResult;
import com.yuan.springcloud.scsrv.gateway.enums.*;
import com.yuan.springcloud.scsrv.gateway.utils.HttpUtils;
import com.yuan.springcloud.scsrv.gateway.utils.JedisManager;
import com.yuan.springcloud.scsrv.gateway.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;

/**
 * ResponseHeaderTokenGatewayFilter
 *
 * @author yuanqing
 * @create 2019-04-10 19:36
 **/
@Component("RefreshAccessTokenGatewayFilterFactory")
public class RefreshAccessTokenGatewayFilterFactory extends AbstractGatewayFilterFactory<RefreshAccessTokenGatewayFilterFactory.Config> {

    private static final Logger logger = LoggerFactory.getLogger(RefreshAccessTokenGatewayFilterFactory.class);

    @Autowired
    private JedisManager jedisManager;

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("refreshTokenBaseFieldName");
    }

    public RefreshAccessTokenGatewayFilterFactory() {
        super(Config.class);
    }

    private Mono<Void> process(ServerWebExchange exchange,Config config) {

        try{

            String refreshTokenBaseFieldValue = HttpUtils.parseQueryParams(exchange.getRequest(), config.refreshTokenBaseFieldName);
            TokenOpeResult tokenOpeResult = new TokenOpeResult();

            if (StringUtils.isEmpty(refreshTokenBaseFieldValue)) {
                tokenOpeResult.setResultMsg(AuthStatus.AUTH_RELOGIN.toString());
                tokenOpeResult.setDetailMsg(TokenRefresh.TOKEN_REFRESH_BASE_FIELD_NOT_EXISTED.toString());
            }

            String refresh_token = HttpUtils.parseHeaderToken(exchange.getRequest().getHeaders(), TokenType.REFRESH_TOKEN.toString());
            int encodeRefreshTokenResult = encodeRefreshTokenResult(refresh_token);

            switch (encodeRefreshTokenResult) {
                case -1:
                    tokenOpeResult.setResultMsg(AuthStatus.REFRESH_TOKEN_SUCCESS.toString());
                    tokenOpeResult.setDetailMsg(TokenRefresh.TOKEN_REFRESH_SUCCESS.toString());
                    String accessToken = storeAccseeToken(refreshTokenBaseFieldValue,refresh_token);
                    HttpUtils.saveRespHeaderToken(exchange.getResponse(),TokenType.ACCESS_TOKEN,accessToken);
                    break;
                case 1: //refresh_tokn not send by client
                    tokenOpeResult.setResultMsg(AuthStatus.REFRESH_TOKEN.toString());
                    tokenOpeResult.setDetailMsg(TokenRefresh.REFRESH_TOKEN_NOT_SEND_BY_CLIENT.toString());
                    break;
                case 2: //refresh_tokn not in cache
                    tokenOpeResult.setResultMsg(AuthStatus.AUTH_RELOGIN.toString());
                    tokenOpeResult.setDetailMsg(TokenRefresh.REFRESH_TOKEN_NOT_EXISTED.toString());
                    break;
                case 3: //refresh_tokn is expired
                    tokenOpeResult.setResultMsg(AuthStatus.AUTH_RELOGIN.toString());
                    tokenOpeResult.setDetailMsg(TokenRefresh.REFRESH_TOKEN_EXPIRED.toString());
                    break;
                case 4: //refresh_tokn is illegal
                    tokenOpeResult.setResultMsg(AuthStatus.AUTH_RELOGIN.toString());
                    tokenOpeResult.setDetailMsg(TokenRefresh.REFRESH_TOKEN_ILLEGAL.toString());
                    break;
            }

            DataBuffer buffer = HttpUtils.buildTokenRespDate(tokenOpeResult, exchange.getResponse());
            return exchange.getResponse().writeWith(Mono.just(buffer));
        }catch (Throwable throwable){

            logger.error(throwable.getMessage(),throwable);
            TokenOpeResult tokenOpeResult = new TokenOpeResult(AuthStatus.AUTH_RELOGIN.toString(),TokenRefresh.TOKEN_REFRESH_FAIL.toString());
            DataBuffer buffer = HttpUtils.buildTokenRespDate(tokenOpeResult, exchange.getResponse());
            return exchange.getResponse().writeWith(Mono.just(buffer));
        }

    }

    private String storeAccseeToken(final String refreshTokenBaseFieldValue,final String refreshToken) throws Exception{

      String accessToken = JwtUtils.createAccessToken(refreshTokenBaseFieldValue);
        JwtEntity jwtEntity = new JwtEntity(accessToken, refreshToken,refreshTokenBaseFieldValue);
        String jsonJwtEntity = JsonUtil.toJSONString(jwtEntity);
        jedisManager.putValue(accessToken,jsonJwtEntity,3, Duration.ONE_DAY.getMilliSeconds()*10);
        jedisManager.delValue(refreshTokenBaseFieldValue);
        jedisManager.putValue(refreshTokenBaseFieldValue,jsonJwtEntity,3, Duration.ONE_DAY.getMilliSeconds()*10);

        return accessToken;
    }

    private int encodeRefreshTokenResult(String refreshToken){

        //refresh_tokn not send by client
        if (StringUtils.isEmpty(refreshToken)){
            return 1;
        }
        //refresh_tokn not in cache
        else if(null==jedisManager.getValueByKey(refreshToken,3)){
            return 2;
        }
        //refresh_tokn is expired
        else if(JwtTokenVerify.TOKEN_EXPIRED == JwtUtils.verifyRefreshTokenV2(refreshToken)){
            return 3;
        }
        //refresh_tokn is illegal
        else if(!(JwtTokenVerify.TOKEN_SUCCESS == JwtUtils.verifyRefreshTokenV2(refreshToken))){
            return 4;
        }
        return -1;
    }

    @Override
    public GatewayFilter apply(Config config) {

//            return new GatewayFilter() {
//                @Override
//                public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//                    return chain.filter(exchange);
//                }
//            };

        return (exchange, chain) -> {
            try{
              return   process(exchange,config);
            }catch (Throwable throwable){

                logger.error(throwable.getMessage(),throwable);
                TokenOpeResult tokenOpeResult= new TokenOpeResult();
                tokenOpeResult.setResultMsg(AuthStatus.AUTH_RELOGIN.toString());
                tokenOpeResult.setDetailMsg(TokenRefresh.TOKEN_REFRESH_FAIL.toString());
                DataBuffer buffer = HttpUtils.buildTokenRespDate(tokenOpeResult,exchange.getResponse());
                return exchange.getResponse().writeWith(Mono.just(buffer));
            }
        };
    }

    public static class Config {

        public String getRefreshTokenBaseFieldName() {
            return refreshTokenBaseFieldName;
        }

        public void setRefreshTokenBaseFieldName(String refreshTokenBaseFieldName) {
            this.refreshTokenBaseFieldName = refreshTokenBaseFieldName;
        }

        @NotEmpty
        private String refreshTokenBaseFieldName;

    }
}
