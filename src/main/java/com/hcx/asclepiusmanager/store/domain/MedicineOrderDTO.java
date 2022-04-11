package com.hcx.asclepiusmanager.store.domain;

import lombok.Data;

import java.util.List;

/**
 * @author huangcaixia
 * @Description
 * @date 2022/4/7 15:20
 */
@Data
public class MedicineOrderDTO {
    /**
     * 地址id
     */
    private Integer addressId;

    /**
     * 总价
     */
    private Integer totalPrice;

    /**
     * 购物车id
     */
    private List<Integer> shopCarIds;
}
