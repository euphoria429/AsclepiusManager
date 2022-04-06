package com.hcx.asclepiusmanager.store.domain;

import lombok.Data;

/**
 * @author huangcaixia
 * @Description TODO
 * @date 2022/4/6 10:12
 */
@Data
public class ShopCarUpdateDTO {
    /**
     * 购物车记录id
     */
    private Integer shopCarId;
    /**
     * 修改数，值为1或-1
     */
    private Integer medicineNumber;
}
