package com.hcx.asclepiusmanager.store.domain;

import lombok.Data;

/**
 * @author huangcaixia
 * @Description
 * @date 2022/4/15 10:46
 */
@Data
public class MedicineOrderQuery {
    private Integer orderId;
    private Integer userId;
    private Integer status;
}
