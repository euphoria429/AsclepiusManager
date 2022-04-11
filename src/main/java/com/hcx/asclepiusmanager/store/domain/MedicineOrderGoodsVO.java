package com.hcx.asclepiusmanager.store.domain;

import lombok.Data;

import java.util.List;

/**
 * @author huangcaixia
 * @Description
 * @date 2022/4/8 16:47
 */
@Data
public class MedicineOrderGoodsVO {
    //[药品id、药品品牌、药品名称、药品数量、药品单价、药品图片list、药品单位]
    private Integer medicineId;
    private String brandName;
    private String medicineName;
    private Integer medicineNumber;
    private Integer medicineUnitPrice;
    private String medicineUnit;
    private List<String> imglist;
}
