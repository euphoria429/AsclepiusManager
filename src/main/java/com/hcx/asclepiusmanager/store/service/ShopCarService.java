package com.hcx.asclepiusmanager.store.service;

import com.hcx.asclepiusmanager.store.domain.ShopCar;
import com.hcx.asclepiusmanager.store.domain.ShopCarDTO;
import com.hcx.asclepiusmanager.store.domain.ShopCarUpdateDTO;
import com.hcx.asclepiusmanager.store.domain.ShopCarVO;

import java.util.List;

/**
 * @author huangcaixia
 * @Description TODO
 * @date 2022/4/2 17:43
 */
public interface ShopCarService {
    Integer saveShopCar(ShopCarDTO shopCarDTO);

    List<ShopCarVO> findUserShopCar();

    Integer updateShopCar(ShopCarUpdateDTO shopCarUpdateDTO);

    Integer deleteShopCar(Integer shopCarId);
}
