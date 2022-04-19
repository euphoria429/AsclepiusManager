package com.hcx.asclepiusmanager.medicine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hcx.asclepiusmanager.medicine.domain.Medicine;
import com.hcx.asclepiusmanager.medicine.domain.MedicineOutInVO;
import com.hcx.asclepiusmanager.medicine.domain.MedicineRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author huangcaixia
 * @date 2022/3/26 12:01
 */
@Mapper
public interface MedicineMapper extends BaseMapper<Medicine> {

    List<Medicine> findMedicineWithPages(@Param("medicineRequest") MedicineRequest medicineRequest);

    List<Medicine> findMedicineWithNumber(@Param("medicineRequest") MedicineRequest medicineRequest);

    @Select("SELECT\n" +
            "\tDATE_FORMAT( mo.created_at, '%Y年%m月%d日' ) days,\n" +
            "\tm.medicine_name,\n" +
            "\tb.brand_name,\n" +
            "\toperation_code,\n" +
            "\tnumber,\n" +
            "\tm.medicine_unit,\n" +
            "\tDATE_FORMAT( mo.created_at, '%H:%m:%s' ) times \n" +
            "FROM\n" +
            "\tmedicine_operated mo\n" +
            "\tLEFT JOIN medicine m ON mo.medicine_id = m.id\n" +
            "\tLEFT JOIN brand b ON m.brand_id = b.id \n" +
            "ORDER BY\n" +
            "\tmo.created_at DESC")
    List<MedicineOutInVO> findMedicineOutInRecord();
}
