package com.hcx.asclepiusmanager.store.service;

import com.hcx.asclepiusmanager.store.domain.MedicineOrderDTO;
import com.hcx.asclepiusmanager.store.domain.MedicineOrderVO;

import java.util.List;

/**
 * @author huangcaixia
 * @Description
 * @date 2022/4/6 21:16
 */
public interface MedicineOrderService {
    Integer saveOrder(MedicineOrderDTO medicineOrderDTO);

    List<MedicineOrderVO> findUserOrder(Integer status);

    MedicineOrderVO getOrderInfo(Integer orderId);
}