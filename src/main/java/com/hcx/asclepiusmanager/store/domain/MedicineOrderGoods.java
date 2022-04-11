package com.hcx.asclepiusmanager.store.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hcx.asclepiusmanager.common.domain.BaseDateAuditPojo;
import lombok.Data;

/**
 * @author huangcaixia
 * @Description
 * @date 2022/4/6 20:54
 */
@Data
public class MedicineOrderGoods {
    /**
     * id自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 订单id
     */
    private Integer medicineOrderId;

    /**
     * 药品id
     */
    private Integer medicineId;

    /**
     * 药品数量
     */
    private Integer medicineNumber;

    /**
     * 商品单价
     */
    private Integer medicineUnitPrice;
}
