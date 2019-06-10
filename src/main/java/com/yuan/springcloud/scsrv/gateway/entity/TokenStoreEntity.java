package com.yuan.springcloud.scsrv.gateway.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * TokenStoreEntity
 *
 * @author yuanqing
 * @create 2019-04-11 22:13
 **/

@Setter
@Getter
public class TokenStoreEntity implements Serializable {

    private String accessToken;
    private String refreshToken;


}
