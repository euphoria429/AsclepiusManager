package com.hcx.asclepiusmanager.medicine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hcx.asclepiusmanager.medicine.domain.MedicineImg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author huangcaixia
 * @date 2022/3/27 22:27
 */
@Mapper
public interface MedicineImgMapper extends BaseMapper<MedicineImg> {

    @Select("select image_id from medicine_img where medicine_id=#{id}")
    List<String> findImgIdsByMedicineId(Integer id);
}
