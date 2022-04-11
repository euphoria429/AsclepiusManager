package com.hcx.asclepiusmanager.medicine.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hcx.asclepiusmanager.common.domain.BaseDateAuditPojo;
import lombok.Data;

/**
 * @author huangcaixia
 * @Description 库存操作记录表
 * @date 2022/3/29 10:52
 */
@Data
public class MedicineOperated extends BaseDateAuditPojo {
    /**
     * id自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 药品id
     */
    private Integer medicineId;

    /**
     * 操作码operationEnum
     */
    private Integer operationCode;

    /**
     * 修改数量
     */
    private Integer number;

    /**
     * 修改后库存
     */
    private Integer afterStore;

    /**
     * 订单id
     */
    private Integer orderId;

}
