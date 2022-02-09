package com.hcx.asclepiusmanager.sysmgr.auth.service;

import com.hcx.asclepiusmanager.sysmgr.auth.domain.SysUser;

/**
 * @ClassName SysUserService
 * @Description TODO
 * @Author hcx
 * @Date 2022/2/9 15:17
 * @Version 1.0
 **/
public interface SysUserService {

    SysUser insert(SysUser sysUser);

    SysUser findByUsername(String username);
}