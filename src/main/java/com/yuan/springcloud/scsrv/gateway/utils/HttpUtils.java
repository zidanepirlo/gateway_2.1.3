package com.yuan.springcloud.scsrv.gateway.utils;

import com.yuan.springcloud.scsrv.gateway.entity.TokenOpeResult;
import com.yuan.springcloud.scsrv.gateway.enums.TokenType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.MultiValueMap;

import javax.xml.ws.http.HTTPBinding;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * HttpUtils
 *
 * @author yuanqing
 * @create 2019-04-11 11:32
 **/
public class HttpUtils {

    private HttpUtils(){

    }

    public static String parseHeaderToken(final HttpHeaders headers, final String fieldName){
        if (null==headers){
            return null;
        }
        List<String> headerList = headers.get(fieldName);
        return headerList == null || headerList.isEmpty() ? null:headerList.get(0);
    }

    public static String parseQueryParams(final ServerHttpRequest request,final String fieldName){
        MultiValueMap<String, String>  queryParamsMap = request.getQueryParams();
        if(queryParamsMap == null || queryParamsMap.isEmpty()){
            return null;
        }
        else{
           return  null == queryParamsMap.get(fieldName)? null:String.valueOf(queryParamsMap.get(fieldName).get(0));
        }
    }

    public static DataBuffer buildTokenRespDate(final TokenOpeResult tokenOpeResult,final ServerHttpResponse response){

        String jsonStr = JsonUtil.toJSONString(tokenOpeResult);
        byte[] bits = jsonStr.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
        return buffer;
    }

    public static void saveRespHeaderToken(final ServerHttpResponse response,TokenType tokenType,String token){
        response.getHeaders().add(tokenType.toString(),token);
    }

}
