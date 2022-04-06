package com.hcx.asclepiusmanager.medicine.domain;

import lombok.Data;

import java.util.List;

/**
 * @author huangcaixia

 * @date 2022/3/28 0:18
 */
@Data
public class MedicineDTO extends Medicine{
    private List<String> medicineImg;
}
