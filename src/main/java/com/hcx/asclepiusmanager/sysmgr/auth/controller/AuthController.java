package com.hcx.asclepiusmanager.sysmgr.auth.controller;

import com.hcx.asclepiusmanager.sysmgr.auth.domain.SysUser;
import com.hcx.asclepiusmanager.sysmgr.auth.service.SysUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName AuthController
 * @Description TODO
 * @Author hcx
 * @Date 2022/2/9 15:07
 * @Version 1.0
 **/
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Resource
    private SysUserService sysUserService;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @PostMapping("/register")
    public String registerUser(@RequestBody Map<String,String> registerUser ){
        SysUser sysUser=new SysUser();
        sysUser.setUsername(registerUser.get("username"));
        sysUser.setPassword(bCryptPasswordEncoder.encode(registerUser.get("password")));
        sysUser.setRole("ROLE_ADMIN");
        return sysUserService.insert(sysUser).toString();
    }



}
