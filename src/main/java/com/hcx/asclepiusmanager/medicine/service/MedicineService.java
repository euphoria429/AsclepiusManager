package com.hcx.asclepiusmanager.medicine.service;


import com.hcx.asclepiusmanager.medicine.domain.*;

import java.util.List;

/**
 * @author huangcaixia
 * @date 2022/3/26 12:02
 */
public interface MedicineService {
    List<MedicineVO> findMedicineWithPages(MedicineRequest medicineRequest);

    Integer saveMedicine(MedicineDTO medicineDTO);

    Integer updateStock(Medicine medicine);

    Integer updateMedicine(Medicine medicine);

    Integer changeSoldStatus(Medicine medicine);

    Integer addStock(Medicine medicine);

    List<MedicineNumberVO> findMedicineWithNumber(MedicineRequest medicineRequest);
}
