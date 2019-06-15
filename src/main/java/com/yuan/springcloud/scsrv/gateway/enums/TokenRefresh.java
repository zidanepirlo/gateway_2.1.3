package com.yuan.springcloud.scsrv.gateway.enums;

public enum TokenRefresh {

    TOKEN_REFRESH_SUCCESS,
    TOKEN_BUILD_FAIL,
    TOKEN_REFRESH_FAIL,
    TOKEN_REFRESH_BASE_FIELD_NOT_EXISTED,
    REFRESH_TOKEN_NOT_SEND_BY_CLIENT,
    REFRESH_TOKEN_NOT_EXISTED,
    REFRESH_TOKEN_EXPIRED,
    REFRESH_TOKEN_ILLEGAL;

}