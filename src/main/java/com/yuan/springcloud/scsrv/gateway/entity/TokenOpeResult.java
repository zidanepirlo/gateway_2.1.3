package com.yuan.springcloud.scsrv.gateway.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * TokenRefreshResult
 *
 * @author yuanqing
 * @create 2019-04-11 10:05
 **/
@Setter
@Getter
public class TokenOpeResult implements Serializable {

    private String resultMsg;
    private String detailMsg;

    public TokenOpeResult(){
        
    }

    public TokenOpeResult(String resultMsg, String detailMsg) {
        this.resultMsg = resultMsg;
        this.detailMsg = detailMsg;
    }
}
