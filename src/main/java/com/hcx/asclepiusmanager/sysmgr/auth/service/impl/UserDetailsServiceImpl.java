package com.hcx.asclepiusmanager.sysmgr.auth.service.impl;

import com.hcx.asclepiusmanager.sysmgr.auth.domain.JwtUser;
import com.hcx.asclepiusmanager.sysmgr.auth.domain.SysUser;
import com.hcx.asclepiusmanager.sysmgr.auth.mapper.SysUserMapper;
import com.hcx.asclepiusmanager.sysmgr.auth.service.SysUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName UserDetailsServiceImpl
 * @Author hcx
 * @Date 2022/2/9 15:09
 * @Version 1.0
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser=sysUserService.findByUsername(username);
        return new JwtUser(sysUser);
    }
}
