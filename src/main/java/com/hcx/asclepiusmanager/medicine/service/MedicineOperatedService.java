package com.hcx.asclepiusmanager.medicine.service;

import com.hcx.asclepiusmanager.medicine.domain.MedicineOperated;

/**
 * @author huangcaixia
 * @date 2022/3/29 10:56
 */
public interface MedicineOperatedService {
    Integer insert(MedicineOperated medicineOperated);

    Integer findMedicineMonthlyNumber(Integer id);
}
