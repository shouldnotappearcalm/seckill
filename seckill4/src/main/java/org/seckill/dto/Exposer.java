package org.seckill.dto;

/**
 * expose seckill address dao
 * Created by GZR on 2016/11/14.
 */
public class Exposer {

    //whether to turn to the seckill(spike)
    private boolean exposed;

    //an encryption measure
    private String md5;

    private long seckillId;

    //system current time(millisecond)
    private long now;

    //open time
    private long start;
    //end time
    private long end;

    public Exposer(boolean exposed, String md5, long seckillId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public Exposer(boolean exposed,long seckillId, long now, long start, long end) {
        this.seckillId=seckillId;
        this.exposed = exposed;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(boolean exposed, long seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }

    public boolean isExposed() {
        return exposed;
    }

    public String getMd5() {
        return md5;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public long getNow() {
        return now;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Exposer{" +
                "exposed=" + exposed +
                ", md5='" + md5 + '\'' +
                ", seckillId=" + seckillId +
                ", now=" + now +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
