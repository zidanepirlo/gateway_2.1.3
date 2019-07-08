package com.yuan.springcloud.scsrv.gateway.filter;

import com.yuan.springcloud.scsrv.gateway.common.JwtUtils;
import com.yuan.springcloud.scsrv.gateway.entity.JwtEntity;
import com.yuan.springcloud.scsrv.gateway.entity.TokenOpeResult;
import com.yuan.springcloud.scsrv.gateway.enums.*;
import com.yuan.springcloud.scsrv.gateway.utils.HttpUtils;
import com.yuan.springcloud.scsrv.gateway.utils.JedisManager;
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


@Component("CheckTokenGatewayFilterFactory")
public  class CheckTokenGatewayFilterFactory extends AbstractGatewayFilterFactory<CheckTokenGatewayFilterFactory.Config> {

    private static final Logger logger = LoggerFactory.getLogger(CheckTokenGatewayFilterFactory.class);

    @Autowired
    private JedisManager jedisManager;

    public CheckTokenGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("tokenTypeStr");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            return process(exchange, chain,config);
        };
    }

    protected Mono<Void> process(ServerWebExchange exchange,GatewayFilterChain chain,Config config) {

        try {

            String token = HttpUtils.parseHeaderToken(exchange.getRequest().getHeaders(),
                    config.tokenType == TokenType.ACCESS_TOKEN ? TokenType.ACCESS_TOKEN.toString() : TokenType.REFRESH_TOKEN.toString());
            int chkTokeResult = encodeChkTokenResult(token,config.tokenType);
            TokenOpeResult tokenOpeResult = new TokenOpeResult();
            switch (chkTokeResult){
                case -1:
                    return chain.filter(exchange);
                case 1: //token not send by client
                    tokenOpeResult.setResultMsg(AuthStatus.AUTH_RELOGIN.toString());
                    tokenOpeResult.setDetailMsg(config.tokenType == TokenType.ACCESS_TOKEN ?
                            TokenCheck.ACCESS_TOKEN_NOT_SEND_BY_CLIENT.toString() : TokenCheck.REFRESH_TOKEN_NOT_SEND_BY_CLIENT.toString());
                    break;
                case 2: //can not get token from cache
                    tokenOpeResult.setResultMsg(AuthStatus.AUTH_RELOGIN.toString());
                    tokenOpeResult.setDetailMsg(config.tokenType == TokenType.ACCESS_TOKEN ?
                            TokenCheck.ACCESS_TOKEN_NOT_EXISTED.toString() : TokenCheck.REFRESH_TOKEN_NOT_EXISTED.toString());
                case 3: //token is expired
                    tokenOpeResult.setResultMsg(AuthStatus.REFRESH_TOKEN.toString());
                    tokenOpeResult.setDetailMsg(config.tokenType == TokenType.ACCESS_TOKEN ?
                            TokenCheck.ACCESS_TOKEN_EXPIRED.toString() : TokenCheck.REFRESH_TOKEN_EXPIRED.toString());
                    break;
                case 4: //token other error
                    tokenOpeResult.setResultMsg(AuthStatus.AUTH_RELOGIN.toString());
                    tokenOpeResult.setDetailMsg(config.tokenType == TokenType.ACCESS_TOKEN ?
                            TokenCheck.ACCESS_TOKEN_ILLEGAL.toString() : TokenCheck.REFRESH_TOKEN_ILLEGAL.toString());
                    break;
                default:
                    return chain.filter(exchange);

            }

            DataBuffer buffer = HttpUtils.buildTokenRespDate(tokenOpeResult, exchange.getResponse());
            return exchange.getResponse().writeWith(Mono.just(buffer));

        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(), throwable);
            TokenOpeResult tokenOpeResult = new TokenOpeResult(AuthStatus.AUTH_RELOGIN.toString(),
                    config.tokenType == TokenType.ACCESS_TOKEN ?
                            TokenCheck.ACCESS_TOKEN_ILLEGAL.toString() : TokenCheck.REFRESH_TOKEN_ILLEGAL.toString());
            DataBuffer buffer = HttpUtils.buildTokenRespDate(tokenOpeResult, exchange.getResponse());
            return exchange.getResponse().writeWith(Mono.just(buffer));
        }
    }


    private int encodeChkTokenResult(final String token,final TokenType tokenType) {

        //token not send by client
        if (StringUtils.isEmpty(token)) {
            return 1;
        }
        //can not get token from cache
        else if (null==jedisManager.getValueByKey(token,3)){
            return 2;
        }
        //token is expired
        else if (JwtTokenVerify.TOKEN_EXPIRED == (tokenType == TokenType.ACCESS_TOKEN ?
                JwtUtils.verifyAccessTokenV2(token):JwtUtils.verifyRefreshTokenV2(token))) {
            return 3;
        }
        //token other error
        else if (!(JwtTokenVerify.TOKEN_SUCCESS == (tokenType == TokenType.ACCESS_TOKEN ?
                JwtUtils.verifyAccessTokenV2(token):JwtUtils.verifyRefreshTokenV2(token)))) {
            return 4;
        }
        return -1;
    }

    public static class Config {

        @NotEmpty
        private String tokenTypeStr;

        private TokenType tokenType;

        public String getTokenTypeStr() {
            return tokenTypeStr;
        }

        public void setTokenTypeStr(String tokenTypeStr) {
            this.tokenTypeStr = tokenTypeStr;
            TokenType tokenTypeCon = TokenType.getTokenTypeByDesc(tokenTypeStr);
            tokenType = null == tokenTypeCon ? TokenType.ACCESS_TOKEN : tokenTypeCon;
        }

        public TokenType getTokenType() {
            return tokenType;
        }

    }
}
