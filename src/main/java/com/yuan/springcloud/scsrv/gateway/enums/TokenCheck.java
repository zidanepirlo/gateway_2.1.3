package com.yuan.springcloud.scsrv.gateway.enums;

public enum TokenCheck {

    ACCESS_TOKEN_EXPIRED("access_token_expired","access_token 过期"),
    REFRESH_TOKEN_EXPIRED("refresh_token_expired","refresh_token 过期"),
    ACCESS_TOKEN_NOT_EXISTED("access_token_not_existed","access_token 不存在"),
    REFRESH_TOKEN_NOT_EXISTED("refresh_token_not_existed","refresh_token 不存在"),
    ACCESS_TOKEN_ILLEGAL("access_token_illegal","access_token 非法"),
    REFRESH_TOKEN_ILLEGAL("refresh_token_illegal","refresh_token 非法"),
    ACCESS_TOKEN_SUCCESS("access_token_success","access_token 校验成功"),
    REFRESH_TOKEN_SUCCESS("refresh_token_success","refresh_token 校验成功"),
    ACCESS_TOKEN_NOT_SEND_BY_CLIENT("access_token_not_send_by_client","客户端未传 access_token"),
    REFRESH_TOKEN_NOT_SEND_BY_CLIENT("refresh_token_not_send_by_client","客户端未传 refresh_token");

    private String code;
    private String desc;

    private TokenCheck(String code, String desc){
        this.code=code;
        this.desc=desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
