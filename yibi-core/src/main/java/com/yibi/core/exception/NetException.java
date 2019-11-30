package com.yibi.core.exception;

/**
 * Created by Administrator on 2018/7/12 0012.
 * 第三方网络连接失败
 */
public class NetException extends RuntimeException {

    public NetException(String message){
        super(message);
    }

}
