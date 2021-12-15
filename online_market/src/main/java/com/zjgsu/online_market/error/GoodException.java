package com.zjgsu.online_market.error;

/**
 * @author SANZONG
 */
public class GoodException extends Exception{
    public GoodException(){
        super();
    }
    public GoodException(String message){
        super(message);
    }
    public GoodException(String message, Throwable cause){
        super(message,cause);
    }


    public GoodException(Throwable cause) {
        super(cause);
    }
}
