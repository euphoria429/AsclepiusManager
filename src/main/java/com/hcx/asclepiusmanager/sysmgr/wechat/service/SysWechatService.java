package com.hcx.asclepiusmanager.sysmgr.wechat.service;

import com.hcx.asclepiusmanager.sysmgr.wechat.domain.SysWechat;

import java.util.Map;

/**
 * @author huangcaixia
 * @date 2022/3/26 19:54
 */
public interface SysWechatService {

    Map authorizedLogin(String code);

    SysWechat updateUserInfo(SysWechat sysWechat);

    SysWechat getUserInfo();
}
