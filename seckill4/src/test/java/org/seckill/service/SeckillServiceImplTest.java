package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by GZR on 2016/11/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class SeckillServiceImplTest {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Resource
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list=seckillService.getSeckillList();
        logger.info("list={}",list);
    }

    @Test
    public void getById() throws Exception {
        long id=10000L;
        Seckill seckill=seckillService.getById(id);
        logger.info("seckill={}",seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long id=10000L;
        Exposer exposer=seckillService.exportSeckillUrl(id);
        logger.info("exposer={}",exposer);
        logger.error("exposed={}",exposer.isExposed());
        logger.error("md5={}",exposer.getMd5());//969c5e56fb145f2ddeca5d16a18744ce
        logger.error("seckillId={}",exposer.getSeckillId());//10000
    }

    //test code complete logic,need pay attention to repeat seckill
    @Test
    public void testLogic() throws Exception {
        long id=10000L;
        Exposer exposer=seckillService.exportSeckillUrl(id);
        if(exposer.isExposed()) {
            logger.info("exposer={}", exposer);
            long phone=13325896325L;
            String md5=exposer.getMd5();
            try {
                SeckillExecution seckillExecution=seckillService.executeSeckill(id,phone,md5);
                logger.info("result={}",seckillExecution);
            }catch (RepeatKillException e){
                logger.error(e.getMessage());
            }catch (SeckillCloseException e1){
                logger.error(e1.getMessage());
            }
        }else{
            //seckill(spike) is not open
            logger.warn("exposer={}",exposer);
        }
    }

    @Test
    public void executeSeckill() throws Exception {
        long id=10000L;
        long userPhone=13345679879L;
        String md5="969c5e56fb145f2ddeca5d16a18744ce";
        try {
            SeckillExecution seckillExecution=seckillService.executeSeckill(id,userPhone,md5);
            logger.info("result={}",seckillExecution);
        }catch (RepeatKillException e1){
            logger.error(e1.getMessage());
        }catch (SeckillCloseException e2){
            logger.error(e2.getMessage());
        }

    }

}