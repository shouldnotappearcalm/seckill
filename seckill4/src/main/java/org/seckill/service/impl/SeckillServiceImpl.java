package org.seckill.service.impl;

import org.seckill.dao.SecKillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by GZR on 2016/11/15.
 */
//@Component @Service @Dao @Controller
@Service("seckillService")
public class SeckillServiceImpl implements SeckillService {

    private Logger logger= LoggerFactory.getLogger(this.getClass());
    //injection Service dependency
    @Resource
    private SecKillDao secKillDao;
    @Resource
    private SuccessKilledDao successKilledDao;
    //md5 salt value
    public  final String salt="dsqwdasdad&8*kkorw2123%";

    public List<Seckill> getSeckillList() {
        return secKillDao.getAll(0,4);
    }

    public Seckill getById(long seckillId) {
        return secKillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill=secKillDao.queryById(seckillId);
        if(seckill==null){
            return new Exposer(false,seckillId);
        }
        Date startTime=seckill.getStartTime();
        Date endTime=seckill.getEndTime();
        //system current time
        Date nowTime=new Date();
        if(nowTime.getTime()<startTime.getTime()||nowTime.getTime()>endTime.getTime())
        {
            return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }
        //process of transforming a particular string
        String md5=getMD5(seckillId);//
        return new Exposer(true,md5,seckillId);
    }

    private String getMD5(long seckillId){
        String base=seckillId+"/"+salt;
        String md5= DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Transactional
    /**
     * advantages of using annotations
     * 1.development team reached a consensus agreement,define the programming style of transaction
     * 2.ensure that the execution time of the method is as short as possible,
     * don't insert network operation like PRC/HTTP request,put it out of transaction
     * 3.not all method need transaction, if there is only one modify operation,
     * just like ready-only operation does not require transaction control
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillCloseException, RepeatKillException, SeckillException {
        try {
            if (md5 == null || !md5.equals(getMD5(seckillId))) {
                throw new SeckillException("seckill date rewrite");
            }
            //execute seckill(spike) logic reduceNumber +add purchase record
            Date nowTime = new Date();
            int updateCount = secKillDao.reduceNumber(seckillId, nowTime);
            if (updateCount <= 0) {
                //no updates to record  ,seckill(spike) is end
                throw new SeckillCloseException("seckill is closed");
            } else {
                //record purchase behaviour
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                //only seckillId,userPhone
                if (insertCount <= 0) {
                    //repeat seckill
                    throw new RepeatKillException("seckill repeated");
                } else {
                    //seckill(spike) success
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
                }
            }
        }catch (SeckillCloseException e1){
            throw  e1;
        }catch (RepeatKillException e2){
            throw e2;
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            //all compile exception will change to runtime exception
            throw new SeckillException("seckill inner error:"+e.getMessage());
        }
    }

    public SecKillDao getSecKillDao() {
        return secKillDao;
    }

    public void setSecKillDao(SecKillDao secKillDao) {
        this.secKillDao = secKillDao;
    }

    public SuccessKilledDao getSuccessKilledDao() {
        return successKilledDao;
    }

    public void setSuccessKilledDao(SuccessKilledDao successKilledDao) {
        this.successKilledDao = successKilledDao;
    }
}
