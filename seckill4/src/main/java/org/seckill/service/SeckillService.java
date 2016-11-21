package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * business interface:stand in user's point of view
 * three aspects 1.Method defined grain size 2.parameter 3.return type
 * Created by GZR on 2016/11/14.
 */
public interface SeckillService {
    /**
     * select all seckill record
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * select a seckill record
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * if seckill is open,output the seckill interface address
     * else output system time and seckill time
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * execute seckill(spike) operation
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)throws SeckillCloseException,RepeatKillException,SeckillException;

}
