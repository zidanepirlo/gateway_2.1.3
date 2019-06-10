package com.yuan.springcloud.scsrv.gateway.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum TokenType {

    ACCESS_TOKEN,
    REFRESH_TOKEN;

    public static TokenType getTokenTypeByDesc(String desc){

        if (StringUtils.isEmpty(desc))
            return null;

        for (TokenType tokenType:TokenType.values()){
            if (desc.equals(tokenType.toString())){
                return tokenType;
            }
        }

        return null;
    }

    public static void main(String[] args) {

       TokenType tokenType = TokenType.getTokenTypeByDesc("ACCESS_TOKEN");
       System.out.println(tokenType.toString());
    }
}
