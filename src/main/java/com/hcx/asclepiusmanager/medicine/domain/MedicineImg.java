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
 * @date 2022/3/27 22:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("medicine_img")
public class MedicineImg extends BaseDateAuditPojo {
    @TableId(type = IdType.INPUT)
    private String imageId;

    private String imageName;

    private String imagePath;

    private Integer medicineId;

}
