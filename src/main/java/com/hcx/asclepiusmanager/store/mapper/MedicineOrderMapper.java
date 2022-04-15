package com.hcx.asclepiusmanager.store.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hcx.asclepiusmanager.store.domain.MedicineOrder;
import com.hcx.asclepiusmanager.store.domain.MedicineOrderQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huangcaixia
 * @Description
 * @date 2022/4/6 21:15
 */
@Mapper
public interface MedicineOrderMapper extends BaseMapper<MedicineOrder> {

    List<MedicineOrder> findOrderByQuery(@Param("medicineOrderQuery") MedicineOrderQuery medicineOrderQuery);
}
