package com.hcx.asclepiusmanager.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hcx.asclepiusmanager.store.domain.MedicineOrderGoods;
import com.hcx.asclepiusmanager.store.mapper.MedicineOrderGoodsMapper;
import com.hcx.asclepiusmanager.store.service.MedicineOrderGoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author huangcaixia
 * @Description
 * @date 2022/4/6 21:17
 */
@Service
public class MedicineOrderGoodsServiceImpl implements MedicineOrderGoodsService {
    @Resource
    MedicineOrderGoodsMapper medicineOrderGoodsMapper;

    @Override
    public Integer insert(MedicineOrderGoods medicineOrderGoods) {
        return medicineOrderGoodsMapper.insert(medicineOrderGoods);
    }

    /**
     * 根据订单id查找订单商品
     * @param id
     * @return
     */
    @Override
    public List<MedicineOrderGoods> findGoodsListByOrderId(Integer id) {
        QueryWrapper<MedicineOrderGoods> wrapper=new QueryWrapper<>();
        wrapper.eq("medicine_order_id",id);
        return medicineOrderGoodsMapper.selectList(wrapper);
    }
}
