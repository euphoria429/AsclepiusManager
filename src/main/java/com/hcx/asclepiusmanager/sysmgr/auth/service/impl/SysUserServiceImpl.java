package com.hcx.asclepiusmanager.sysmgr.auth.service.impl;

import com.hcx.asclepiusmanager.sysmgr.auth.domain.SysUser;
import com.hcx.asclepiusmanager.sysmgr.auth.mapper.SysUserMapper;
import com.hcx.asclepiusmanager.sysmgr.auth.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName SysUserServiceImpl
 * @Description TODO
 * @Author hcx
 * @Date 2022/2/9 15:18
 * @Version 1.0
 **/
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    SysUserMapper sysUserMapper;

    @Override
    public SysUser insert(SysUser sysUser) {
       sysUserMapper.insert(sysUser);
       return sysUser;
    }

    @Override
    public SysUser findByUsername(String username) {
        return sysUserMapper.findByUsername(username);
    }
}
