package com.hcx.asclepiusmanager.store.domain;

import lombok.Data;

/**
 * @author huangcaixia
 * @Description TODO
 * @date 2022/4/6 10:49
 */
@Data
public class ShopCarVO extends ShopCar{
    /**
     * 药品品牌名
     */
    private String brandName;
    /**
     * 药品名称
     */
    private String medicineName;
    /**
     * 药品单位
     */
    private String medicineUnit;
    /**
     * 药品单价
     */
    private Integer medicineUnitPrice;
}
