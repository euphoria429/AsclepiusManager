package com.hcx.asclepiusmanager.sysmgr.auth.domain;

import lombok.Data;

/**
 * @ClassName SysUserLoginDTO
 * @Author hcx
 * @Date 2022/2/9 15:03
 * @Version 1.0
 **/
@Data
public class SysUserLoginDTO {
    private String username;
    private String password;
    private Integer rememberMe;
    private String keyCode;
    private String captcha;
}
