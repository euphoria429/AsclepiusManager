package com.hcx.asclepiusmanager.sysmgr.auth.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hcx.asclepiusmanager.common.domain.BaseDateAuditPojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName SysUser
 * @Author hcx
 * @Date 2022/1/25 17:26
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class SysUser extends BaseDateAuditPojo {

    /**
     * id自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private String role;

}

