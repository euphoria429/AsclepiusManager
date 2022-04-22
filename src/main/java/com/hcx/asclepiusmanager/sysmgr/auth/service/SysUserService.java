package com.hcx.asclepiusmanager.sysmgr.auth.service;

import com.hcx.asclepiusmanager.sysmgr.auth.domain.SysUser;

/**
 * @ClassName SysUserService
 * @Author hcx
 * @Date 2022/2/9 15:17
 * @Version 1.0
 **/
public interface SysUserService {

    SysUser insert(SysUser sysUser);

    SysUser findByUsername(String username);

    String getCurrentOperator();

    Integer addAdmin(String username);

    Integer changePwd(String oldPwd, String newPwd);
}
