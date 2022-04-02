package com.hcx.asclepiusmanager.medicine.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hcx.asclepiusmanager.common.domain.BaseDateAuditPojo;
import lombok.Data;
import org.springframework.http.HttpEntity;

import java.util.List;

/**
 * @author huangcaixia
 * @Description TODO
 * @date 2022/3/28 1:26
 */
@Data
public class MedicineVO extends BaseDateAuditPojo {
    /**
     * id
     */
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

    /**
     * 药品品牌名
     */
    private String brandName;

    /**
     * 药品类别名
     */
    private String medicineTypeName;

    /**
     * 药品图片(带请求头
     */
    private List<String> medicineImgs;
}
