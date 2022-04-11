package com.hcx.asclepiusmanager.store.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hcx.asclepiusmanager.common.domain.BaseDateAuditPojo;
import lombok.Data;

import java.util.Date;

/**
 * @author huangcaixia
 * @Description
 * @date 2022/4/6 20:50
 */
@Data
public class MedicineOrder extends BaseDateAuditPojo {
    /**
     * id自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 地址id
     */
    private Integer addressId;

    /**
     * 总价
     */
    private Integer totalPrice;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 发货时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date deliveryAt;

    /**
     * 收货时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date receiptAt;
}
