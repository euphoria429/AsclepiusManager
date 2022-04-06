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
 * @date 2022/3/26 12:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("brand")
public class Brand extends BaseDateAuditPojo {
    /**
     * id自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 品牌商名称
     */
    private String brandName;
}
