package com.hcx.asclepiusmanager.sysmgr.auth.service.impl;

import com.hcx.asclepiusmanager.sysmgr.auth.domain.SysUser;
import com.hcx.asclepiusmanager.sysmgr.auth.mapper.SysUserMapper;
import com.hcx.asclepiusmanager.sysmgr.auth.service.SysUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * @ClassName SysUserServiceImpl
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

    /**
     * 获取当前用户权限
     *
     * @return
     */
    public String getCurrentOperator() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role="";
        for (GrantedAuthority authority : authorities) {
            role = authority.getAuthority();
        }
        return role;
    }
}
