package org.seckill.enums;

/**
 * Created by GZR on 2016/11/15.
 */
public enum SeckillStateEnum {
    SUCCESS(1,"success seckill"),
    END(0,"enf of seckill"),
    REPEAT_KILL(-1,"repeat seckill"),
    INNER_ERROR(-2,"system unusual"),
    DATE_REWRITE(-3,"data tamper");
    private int state;
    private String stateinfo;

    SeckillStateEnum(int state, String stateinfo) {
        this.state = state;
        this.stateinfo = stateinfo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateinfo() {
        return stateinfo;
    }

    public void setStateinfo(String stateinfo) {
        this.stateinfo = stateinfo;
    }
    public static SeckillStateEnum stateOf(int index){
        for(SeckillStateEnum state:values()){
            if(state.getState()==index){
                return state;
            }
        }
        return null;
    }
}
