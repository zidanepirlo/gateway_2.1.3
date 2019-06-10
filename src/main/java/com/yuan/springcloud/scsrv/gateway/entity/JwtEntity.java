package com.yuan.springcloud.scsrv.gateway.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * JwtEntity
 *
 * @author yuanqing
 * @create 2019-04-09 13:25
 **/
@Setter
@Getter
public class JwtEntity implements Serializable {

    private String accessToken;

    private String refreshToken;

    private String userId;

    public JwtEntity() {
    }

    public JwtEntity(String accessToken, String refreshToken, String userId) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "JwtEntity {" +
                "accessToken='" + accessToken + '\'' +
                "refreshToken='" + refreshToken + '\'' +
                "userId='" + userId + '\'' +
                '}';
    }
}
