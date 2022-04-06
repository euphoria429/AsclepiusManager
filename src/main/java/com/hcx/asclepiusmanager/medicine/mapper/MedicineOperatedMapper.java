package com.hcx.asclepiusmanager.medicine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hcx.asclepiusmanager.medicine.domain.MedicineOperated;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author huangcaixia
 * @date 2022/3/29 10:56
 */
@Mapper
public interface MedicineOperatedMapper extends BaseMapper<MedicineOperated> {

    Integer findMedicineMonthlyNumber(@Param("medicineId") Integer id, @Param("operationCode") Integer code);
}
