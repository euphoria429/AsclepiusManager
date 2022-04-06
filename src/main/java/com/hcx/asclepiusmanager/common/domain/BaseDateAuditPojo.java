package com.hcx.asclepiusmanager.common.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName BaseDateAuditPojo
 * @Author hcx
 * @Date 2022/2/9 14:44
 * @Version 1.0
 **/
@Data
@Accessors(chain=true)
public class BaseDateAuditPojo implements Serializable {
    /**
     * 创建该记录的时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    /**
     * 创建和更新该记录的时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;
}
