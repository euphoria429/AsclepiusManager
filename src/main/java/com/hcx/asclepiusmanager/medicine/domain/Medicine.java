package com.hcx.asclepiusmanager.medicine.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hcx.asclepiusmanager.common.domain.BaseDateAuditPojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huangcaixia
 * @date 2022/3/26 11:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("medicine")
public class Medicine extends BaseDateAuditPojo {
    /**
     * id自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 药品名称
     */
    private String medicineName;
    /**
     * 药品品牌编号
     */
    private Integer brandId;

    /**
     * 药品简介
     */
    private String explanation;

    /**
     *  药品库存
     */
    private Integer medicineStore;

    /**
     * 药品价格（厘）
     */
    private Integer medicineUnitPrice;

    /**
     * 药品单位
     */
    private String medicineUnit;

    /**
     * 药品类别id
     */
    private Integer medicineTypeId;

    /**
     * 药品状态
     */
    private Integer status;


}
