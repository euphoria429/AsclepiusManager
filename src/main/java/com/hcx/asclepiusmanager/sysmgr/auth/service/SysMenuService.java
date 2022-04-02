package com.hcx.asclepiusmanager.sysmgr.auth.service;

import java.util.Map;

/**
 * @author huangcaixia
 * @Description TODO
 * @date 2022/3/30 11:04
 */
public interface SysMenuService {
    Map<String, Object> getMenu(String role);
}
