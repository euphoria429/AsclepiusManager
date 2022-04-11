package com.hcx.asclepiusmanager.common.enums;

import lombok.Getter;

/**
 * @author huangcaixia
 * @Description 订单状态枚举类
 * @date 2022/4/7 15:29
 */
@Getter
public enum OrderEnum {
    SOLD(1,"待发货"),
    RESTOCK(2,"已发货"),
    MODIFY(3,"已完成"),
    ;

    private Integer code;
    private String msg;

    OrderEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 根据状态码查找状态信息
     * @param code
     * @return
     */
    public static StringBuffer findByCode(Integer code){
        StringBuffer orderStatusMsg=new StringBuffer();
        for (OrderEnum orderEnum : OrderEnum.values()){
            if(code== orderEnum.code){
                orderStatusMsg.append(orderEnum.msg);
                break;
            }
        }
        return orderStatusMsg;
    }
}
