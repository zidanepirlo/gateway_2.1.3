package com.yuan.springcloud.scsrv.gateway.enums;

import lombok.Getter;

import java.util.Calendar;

@Getter
public enum TokenDuration {

    SECOND(Calendar.SECOND, "SECOND"),
    MINUTE(Calendar.MINUTE, "MINUTE"),
    HOUR(Calendar.HOUR, "HOUR");

    int calendarDate;
    String code;

    private TokenDuration(int calendarDate, String code) {
        this.calendarDate = calendarDate;
        this.code = code;
    }


    public static TokenDuration getTokenDuration(String code) {

        TokenDuration result = TokenDuration.MINUTE;
        for (TokenDuration tokenDuration : TokenDuration.values()) {
            if (code.equals(tokenDuration.code)) {
                result = tokenDuration;
                return result;
            }
        }
        return result;
    }

    public long getMilliSeconds() {

        switch (this) {
            case SECOND:
                return 1000L;
            case MINUTE:
                return 60 * 1000L;
            case HOUR:
                return 24 * 60 * 1000L;
            default:
                return 10 * 1000L;
        }
    }
}
