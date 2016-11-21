package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by GZR on 2016/11/14.
 * configure spring integration junit,load springIoc when junit start
 */
@RunWith(SpringJUnit4ClassRunner.class)
//tell junit the spring configure file
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SecKillDaoTest {

    //rejection Dao class depend
    @Resource
    private SecKillDao secKillDao;



    @Test
    public void queryById() throws Exception {
        long id=10000;
        Seckill seckill=secKillDao.queryById(id);
        System.out.println(seckill.getName());
    }

    @Test
    public void getAll() throws Exception {
        List<Seckill> seckillList=secKillDao.getAll(0,100);
        for(Seckill seckill:seckillList){
            System.out.println(seckill);
        }
    }

    @Test
    public void reduceNumber() throws Exception {
        Date killTime=new Date();
        int updateCount=secKillDao.reduceNumber(10000L,killTime);
        System.out.println("updateCount:"+updateCount);
    }

}