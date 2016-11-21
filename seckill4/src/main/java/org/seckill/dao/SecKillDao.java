package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;

/**
 * Created by GZR on 2016/11/10.
 */
public interface SecKillDao {

    /**
     *减少库存
     * @param seckillId
     * @param killTime
     * @return if influenced row number greater than one,this result expresses updated record number of rows
     */
    int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime") Date killTime);

    /**
     *根据ID查询秒杀对象
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 根据偏移量查询商品列表
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> getAll(@Param("offset") int offset,@Param("limit")int limit);

}
