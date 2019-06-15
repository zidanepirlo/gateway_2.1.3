package com.yuan.springcloud.scsrv.gateway.enums;

/**
 * @author gaochuanjun
 * @since 15/12/25
 */
public enum Duration {
    THIRTY_SEC,
    FIVE_SEC,
    ONE_MIN,
    TWO_MIN,
    THREE_MIN,
    FIVE_MIN,
    SIX_MIN,
    TEN_MIN,
    THIRTY_MIN,
    ONE_HOUR,
    TWO_HOUR,
    THREE_HOUR,
    ONE_DAY;

    public static Duration getDuration(String desc){

        Duration result = Duration.TEN_MIN;
        for (Duration duration:Duration.values()){
            if (desc.equals(duration.toString())){
                return duration;
            }
        }

        return result;
    }

    public static Duration findDuration(int minute) {
        switch (minute) {
            case 1:
                return ONE_MIN;
            case 2:
                return TWO_MIN;
            case 3:
                return THREE_MIN;
            case 5:
                return FIVE_MIN;
            case 6:
                return SIX_MIN;
            case 10:
                return TEN_MIN;
            case 30:
                return THIRTY_MIN;
            case 60:
                return ONE_HOUR;
            case 120:
                return TWO_HOUR;
            case 180:
                return THREE_HOUR;
            case 1440:
                return ONE_DAY;
            default:
                return ONE_DAY;
        }
    }

    public long getMilliSeconds() {
        switch (this) {
            case FIVE_SEC:
                return 5000L;
            case THIRTY_SEC:
                return 30000L;
            case ONE_MIN:
                return 60000L;
            case TWO_MIN:
                return 2*60000L;
            case THREE_MIN:
                return 3*60000L;
            case FIVE_MIN:
                return 5*60000L;
            case SIX_MIN:
                return 6*60000L;
            case TEN_MIN:
                return 10*60000L;
            case THIRTY_MIN:
                return 30*60000L;
            case ONE_HOUR:
                return 60*60000L;
            case TWO_HOUR:
                return 2*60*60000L;
            case THREE_HOUR:
                return 3*60*60000L;
            case ONE_DAY:
                return 24*60*60000L;
            default:
                return 24*60*60000L;
        }
    }
}
