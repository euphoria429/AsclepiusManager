package com.hcx.asclepiusmanager.medicine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hcx.asclepiusmanager.medicine.domain.Medicine;
import com.hcx.asclepiusmanager.medicine.domain.MedicineRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huangcaixia
 * @date 2022/3/26 12:01
 */
@Mapper
public interface MedicineMapper extends BaseMapper<Medicine> {

    List<Medicine> findMedicineWithPages(@Param("medicineRequest") MedicineRequest medicineRequest);
}
