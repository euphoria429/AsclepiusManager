package com.hcx.asclepiusmanager.common.enums;

import lombok.Getter;

/**
 * @author huangcaixia
 * @date 2022/3/29 14:16
 */
@Getter
public enum SoldStatusEnum {
    ON_SOLD(1, "在售"),
    OFF_SOLD(0, "下架");
    private Integer code;
    private String msg;

    SoldStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 根据状态码查找状态信息
     * @param code
     * @return
     */
    public static StringBuffer findByCode(Integer code){
        StringBuffer soldStatusMsg=new StringBuffer();
        for (SoldStatusEnum soldStatusEnum : SoldStatusEnum.values()){
            if(code== soldStatusEnum.code){
                soldStatusMsg.append(soldStatusEnum.msg);
                break;
            }
        }
        return soldStatusMsg;
    }
}
