package com.hcx.asclepiusmanager.store.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hcx.asclepiusmanager.common.domain.BaseDateAuditPojo;
import lombok.Data;

/**
 * @author huangcaixia
 * @Description TODO
 * @date 2022/3/28 21:28
 */
@Data
public class Address extends BaseDateAuditPojo {
    /**
     * id自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 省市区
     */
    private String location;

    /**
     * 详细地址
     */
    private String detailAddress;

    /**
     * 收件人电话
     */
    private String recipientPhone;

    /**
     * 收件人姓名
     */
    private String recipientName;

    /**
     * 用户id
     */
    private String userOpenId;

}
