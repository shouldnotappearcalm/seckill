-- 数据库初始化脚本
-- 创建数据库
CREATE DATABASE seckill;

-- 使用数据库
USE seckill;

-- 创建秒杀库存表
CREATE TABLE seckill (
	seckill_id BIGINT NOT NULL auto_increment COMMENT '商品库存ID',
	name VARCHAR (120) NOT NULL COMMENT '商品名称',
	number INT NOT NULL COMMENT '库存数量',
	start_time TIMESTAMP NOT NULL COMMENT '秒杀开启时间',
	end_time TIMESTAMP NOT NULL COMMENT '秒杀结束时间',
	create_time TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00 ' COMMENT '创建时间',
	primary key (seckill_id),
	key idx_start_time(start_time),
	key idx_end_time(end_time),
	key idx_create_time(create_time)
) ENGINE = INNODB auto_increment = 10000 DEFAULT charset = utf8 COMMENT = '秒杀库存表';

-- 初始化数据
insert into seckill(name,number,start_time,end_time)
values('1000秒杀iphone7',100,'2016-11-11 00:00:00','2016-11-12 00:00:00'),
('500秒杀Ipad2',200,'2016-11-11 00:00:00','2016-11-12 00:00:00'),
('500秒杀小米Mix',300,'2016-11-11 00:00:00','2016-11-12 00:00:00'),
('500秒杀小米Note2',400,'2016-11-11 00:00:00','2016-11-12 00:00:00');

-- 秒杀成功明细表
-- 用户登录认证的相关信息


CREATE TABLE success_killed (
	seckill_id BIGINT NOT NULL COMMENT '秒杀商品ID',
	user_phone BIGINT NOT NULL COMMENT '用户手机号',
	state TINYINT NOT NULL DEFAULT - 1 COMMENT '状态标识 -1：无效 0：成功 1：已付款',
	create_time TIMESTAMP NOT NULL COMMENT '创建时间',
	PRIMARY KEY (seckill_id, user_phone),
	KEY idx_create_time (create_time)
) ENGINE = INNODB DEFAULT charset = utf8 COMMENT = '秒杀成功明细表';

--连接到数据库控制台
mysql -uroot -p

-- 上线V1.1
ALTER TABLE seckill;

DROP INDEX idx_create_time;

ADD INDEX idx_c_s (start_time, create_time);