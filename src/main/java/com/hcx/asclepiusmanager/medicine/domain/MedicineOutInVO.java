package com.hcx.asclepiusmanager.medicine.domain;
import lombok.Data;


/**
 * @author huangcaixia
 * @Description
 * @date 2022/4/18 15:34
 */
@Data
public class MedicineOutInVO {
    /**
     * 操作类型
     */
    private String operation;

    private Integer operationCode;
    /**
     * 数量
     */
    private Integer number;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 药品名称
     *
     *
     */
    private String medicineName;

    /**
     * 药品单位
     */
    private String medicineUnit;

    /**
     * 创建日期
     */
    private String days;
    /**
     * 创建时间
     */
    private String times;
}
