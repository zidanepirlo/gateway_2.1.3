package com.yuan.springcloud.scsrv.gateway.enums;

public enum TokenBuild {

    TOKEN_BUILD_SUCCESS("token_build_success","token 生成成功"),
    TOKEN_BUILD_FAIL("token_build_fail","token 生成失败");

    private String code;
    private String desc;

    private TokenBuild(String code, String desc){
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
