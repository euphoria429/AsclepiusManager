package com.hcx.asclepiusmanager.sysmgr.auth.domain;

import lombok.Data;

/**
 * @ClassName sys_admin_user
 * @Description TODO
 * @Author hcx
 * @Date 2022/1/25 17:26
 * @Version 1.0
 **/
@Data
public class SysAdminUser {
    private Long id;
    private String userName;
    private String nickName;
    private String password;

}
