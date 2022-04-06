package com.hcx.asclepiusmanager.medicine.service.impl;

import com.hcx.asclepiusmanager.common.enums.OperationEnum;
import com.hcx.asclepiusmanager.medicine.domain.MedicineOperated;
import com.hcx.asclepiusmanager.medicine.mapper.MedicineOperatedMapper;
import com.hcx.asclepiusmanager.medicine.service.MedicineOperatedService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author huangcaixia
 * @date 2022/3/29 10:57
 */
@Service
public class MedicineOperatedServiceImpl implements MedicineOperatedService {

    @Resource
    MedicineOperatedMapper medicineOperatedMapper;

    @Override
    public Integer insert(MedicineOperated medicineOperated) {
        return medicineOperatedMapper.insert(medicineOperated);
    }

    /**
     * 查询药品月售量
     * @param id
     * @return
     */
    @Override
    public Integer findMedicineMonthlyNumber(Integer id) {
        return medicineOperatedMapper.findMedicineMonthlyNumber(id, OperationEnum.SOLD.getCode());
    }
}
