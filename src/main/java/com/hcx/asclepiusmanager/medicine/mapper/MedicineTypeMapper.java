package com.hcx.asclepiusmanager.medicine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hcx.asclepiusmanager.medicine.domain.MedicineType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author huangcaixia
 * @date 2022/3/26 12:13
 */
@Mapper
public interface MedicineTypeMapper extends BaseMapper<MedicineType> {
    /**
     * 根据药品类型名称查询
     * @param medicineTypeName
     * @return
     */
    @Select("select * from medicine_type where medicine_type_name=#{name}")
    List<MedicineType> findByMedicineTypeName(@Param("name")  String medicineTypeName);

    List<MedicineType> findMedicineTypeWithPages(@Param("medicineType") MedicineType medicineType);
}
