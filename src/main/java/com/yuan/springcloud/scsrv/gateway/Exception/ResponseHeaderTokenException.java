package com.yuan.springcloud.scsrv.gateway.Exception;

/**
 * ResponseHeaderTokenException
 *
 * @author yuanqing
 * @create 2019-04-11 08:49
 **/
public class ResponseHeaderTokenException extends Exception{

    public ResponseHeaderTokenException(){
        super();
    }

    public ResponseHeaderTokenException(String message){
        super(message);
    }

    public ResponseHeaderTokenException(String message,Throwable cause){
        super(message,cause);
    }

    public ResponseHeaderTokenException(Throwable cause){
        super(cause);
    }
}
