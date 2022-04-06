package com.hcx.asclepiusmanager.medicine.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hcx.asclepiusmanager.medicine.domain.Brand;
import com.hcx.asclepiusmanager.medicine.domain.MedicineType;
import com.hcx.asclepiusmanager.medicine.mapper.MedicineTypeMapper;
import com.hcx.asclepiusmanager.medicine.service.MedicineTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author huangcaixia
 * @date 2022/3/26 12:14
 */
@Service
public class MedicineTypeServiceImpl implements MedicineTypeService {
    @Resource
    MedicineTypeMapper medicineTypeMapper;

    @Override
    public Integer saveMedicineType(MedicineType medicineType) {
        if(StringUtils.isNotBlank(medicineType.getMedicineTypeName())) {
            List<MedicineType> medicineTypeList = medicineTypeMapper.findByMedicineTypeName(medicineType.getMedicineTypeName());
            if(!medicineTypeList.isEmpty()) {
                return 0;
            }
        } else {
            return 0;
        }
        return medicineTypeMapper.insert(medicineType);
    }

    @Override
    public List findMedicineTypeWithPages(MedicineType medicineType) {
        List<MedicineType> medicineTypeList = medicineTypeMapper.findMedicineTypeWithPages(medicineType);
        return medicineTypeList;
    }

    @Override
    public List findAllMedicineType() {
        MedicineType medicineType=new MedicineType();
        return medicineTypeMapper.findMedicineTypeWithPages(medicineType);
    }

    @Override
    public MedicineType findMedicineTypeById(Integer medicineTypeId) {
        return medicineTypeMapper.selectById(medicineTypeId);
    }
}
