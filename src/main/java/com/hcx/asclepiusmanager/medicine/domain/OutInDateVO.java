package com.hcx.asclepiusmanager.medicine.domain;

import lombok.Data;

import java.util.List;

/**
 * @author huangcaixia
 * @Description
 * @date 2022/4/19 10:24
 */
@Data
public class OutInDateVO {
    private String days;
    private List<MedicineOutInVO> medicineOutInVOS;
}
