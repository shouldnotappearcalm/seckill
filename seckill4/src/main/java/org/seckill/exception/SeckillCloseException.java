package org.seckill.exception;

/**
 * seckill(spike) close exception
 * Created by GZR on 2016/11/14.
 */
public class SeckillCloseException extends  SeckillException{

    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
