package com.yuan.springcloud.scsrv.gateway.enums;

public enum AuthStatus {

    AUTH_SUCCESS("auth_success","登录成功"),
    AUTH_FAIL("auth_fail","登录失败"),
    AUTH_RELOGIN("auth_relogin","重新登录"),
    REFRESH_TOKEN("refresh_token","刷新token"),
    REFRESH_TOKEN_SUCCESS("refresh_token_success","刷新token成功");

    private String code;
    private String desc;

    private AuthStatus(String code,String desc){
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
