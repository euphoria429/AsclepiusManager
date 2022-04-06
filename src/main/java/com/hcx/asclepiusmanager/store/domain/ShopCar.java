package com.hcx.asclepiusmanager.store.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hcx.asclepiusmanager.common.domain.BaseDateAuditPojo;
import lombok.Data;

/**
 * @author huangcaixia
 * @Description 购物车表
 * @date 2022/4/2 17:28
 */
@Data
public class ShopCar extends BaseDateAuditPojo {
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
     * 用户id
     */
    private Integer userId;

    /**
     * 药品数量
     */
    private Integer medicineNumber;

    /**
     * 购物车状态
     */
    private Integer status;

}
