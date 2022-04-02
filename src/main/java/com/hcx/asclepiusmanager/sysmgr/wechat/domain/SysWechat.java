package com.hcx.asclepiusmanager.sysmgr.wechat.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hcx.asclepiusmanager.common.domain.BaseDateAuditPojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huangcaixia
 * @Description TODO
 * @date 2022/3/26 19:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_wechat")
public class SysWechat extends BaseDateAuditPojo {
    /**
     * 用户id自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 微信openid
     */
    private String openId;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 用户性别 1 or 0
     */
    private Integer gender;

    /**
     * 用户昵称
     */
    private String nickName;
}
