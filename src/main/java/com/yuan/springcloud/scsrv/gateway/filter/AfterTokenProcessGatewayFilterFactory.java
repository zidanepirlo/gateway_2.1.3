package com.yuan.springcloud.scsrv.gateway.filter;

import com.yuan.springcloud.scsrv.gateway.utils.HttpUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.ResolvableType;
import org.springframework.core.codec.ByteArrayDecoder;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.DecoderHttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * AfterTokenProcessGatewayFilter
 *
 * @author yuanqing
 * @create 2019-04-10 16:35
 **/
@Component("AfterTokenProcessGatewayFilterFactory")
public class AfterTokenProcessGatewayFilterFactory extends AbstractGatewayFilterFactory<AfterTokenProcessGatewayFilterFactory.Config>{

    private static final Logger logger = LoggerFactory.getLogger(AfterTokenProcessGatewayFilterFactory.class);

    public AfterTokenProcessGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

//          String  value1 = HttpUtils.parseQueryParams(exchange.getRequest(),"value1");
//          String  value2 =  HttpUtils.parseQueryParams(exchange.getRequest(),"value2");
//
//            logger.info("value1={}",value1);
//            logger.info("value2={}",value2);
//            logger.info(resolveBodyFromRequest(exchange.getRequest()));

            Flux<DataBuffer> dataBufferFlux = exchange.getRequest().getBody();
            return chain.filter(exchange);
        };
    }

//    private String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest){
//        //获取请求体
//        Flux<DataBuffer> body = serverHttpRequest.getBody();
//        StringBuilder sb = new StringBuilder();
//
//        body.subscribe(buffer -> {
//            byte[] bytes = new byte[buffer.readableByteCount()];
//            buffer.read(bytes);
//            DataBufferUtils.release(buffer);
//            String bodyString = new String(bytes, StandardCharsets.UTF_8);
//            sb.append(bodyString);
//        });
//        return sb.toString();
//
//    }


//    private String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest) {
//        //获取请求体
//        Flux<DataBuffer> body = serverHttpRequest.getBody();
//        System.out.println(body);
//        AtomicReference<String> bodyRef = new AtomicReference<>();
//        body.subscribe(buffer -> {
//            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
//            DataBufferUtils.release(buffer);
//            bodyRef.set(charBuffer.toString());
//        });
//        //获取request body
//        return bodyRef.get();
//    }

    public static class Config {

    }
}
