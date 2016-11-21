package org.seckill.exception;

/**
 * repeat seckill(spike) exception(runtime exception)
 * Created by GZR on 2016/11/14.
 */
public class RepeatKillException extends  SeckillException{

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
