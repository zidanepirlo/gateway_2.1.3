package com.yuan.springcloud.scsrv.gateway.enums;

public enum TokenRefOperatorEntity {

    RELOGIN("relogin", "重写登录"),
    REFRESH_TOKEN("refresh_token", "刷新token");

    String code;
    String desc;

    private TokenRefOperatorEntity(String code, String desc) {
        this.code = code;
        this.desc = desc;
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
