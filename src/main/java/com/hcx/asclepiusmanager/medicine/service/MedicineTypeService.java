package com.hcx.asclepiusmanager.medicine.service;

import com.hcx.asclepiusmanager.medicine.domain.MedicineType;

import java.util.List;

/**
 * @author huangcaixia
 * @date 2022/3/26 12:13
 */
public interface MedicineTypeService {
    Integer saveMedicineType(MedicineType medicineType);

    List findMedicineTypeWithPages(MedicineType medicineType);

    List findAllMedicineType();

    MedicineType findMedicineTypeById(Integer medicineTypeId);
}
