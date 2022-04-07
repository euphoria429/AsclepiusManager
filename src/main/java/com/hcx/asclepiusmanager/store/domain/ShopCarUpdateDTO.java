package com.hcx.asclepiusmanager.store.domain;

import lombok.Data;

/**
 * @author huangcaixia
 * @date 2022/4/6 10:12
 */
@Data
public class ShopCarUpdateDTO {
    /**
     * 购物车记录id
     */
    private Integer shopCarId;
    /**
     * 修改的数值
     */
    private Integer medicineNumber;
}
