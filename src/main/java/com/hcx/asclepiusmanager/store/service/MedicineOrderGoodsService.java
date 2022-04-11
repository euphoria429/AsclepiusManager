package com.hcx.asclepiusmanager.store.service;

import com.hcx.asclepiusmanager.store.domain.MedicineOrderGoods;

import java.util.List;

/**
 * @author huangcaixia
 * @Description
 * @date 2022/4/6 21:17
 */
public interface MedicineOrderGoodsService {
    Integer insert(MedicineOrderGoods medicineOrderGoods);

    List<MedicineOrderGoods> findGoodsListByOrderId(Integer id);
}
