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
 * @date 2022/3/26 12:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("medicine_type")
public class MedicineType extends BaseDateAuditPojo {
    /**
     * id自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 药品类型名称
     */
    private String medicineTypeName;
}
