package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

/**
 * Created by GZR on 2016/11/10.
 */
public interface SuccessKilledDao {

    /**
     * insert purchase detail,repeat will be filter
     * @param seckillId
     * @param userPhone
     * @return insert number of rows
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * select SuccessKilled and Seckill entity object by id
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

}
