package com.hcx.asclepiusmanager.common.enums;

import lombok.Getter;

/**
 * @author huangcaixia
 * @Description TODO
 * @date 2022/3/29 10:48
 */
@Getter
public enum OperationEnum {
    SOLD(1,"售出"),
    RESTOCK(2,"进货"),
    MODIFY(3,"手动修改"),
    ;

    private Integer code;
    private String msg;

    OperationEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 根据状态码查找状态信息
     * @param code
     * @return
     */
    public static StringBuffer findByCode(Integer code){
        StringBuffer operationMsg=new StringBuffer();
        for (OperationEnum operationEnum : OperationEnum.values()){
            if(code== operationEnum.code){
                operationMsg.append(operationEnum.msg);
                break;
            }
        }
        return operationMsg;
    }
}
