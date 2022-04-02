package com.hcx.asclepiusmanager.medicine.domain;

import lombok.Data;

/**
 * @author huangcaixia
 * @Description 搜索条件
 * @date 2022/3/27 13:33
 */
@Data
public class MedicineRequest {
    private Integer id;
    private String medicineName;
    private Integer brandId;
    private Integer medicineTypeId;
    private Integer status;
}
