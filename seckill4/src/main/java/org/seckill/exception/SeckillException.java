package org.seckill.exception;

/**
 * seckill(spike) related business exception
 * Created by GZR on 2016/11/14.
 */
public class SeckillException extends  RuntimeException{

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
