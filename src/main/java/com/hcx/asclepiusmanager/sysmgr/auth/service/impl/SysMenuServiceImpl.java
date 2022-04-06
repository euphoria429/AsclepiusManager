package com.hcx.asclepiusmanager.sysmgr.auth.service.impl;

import com.hcx.asclepiusmanager.sysmgr.auth.domain.MenuVO;
import com.hcx.asclepiusmanager.sysmgr.auth.domain.TopMenu;
import com.hcx.asclepiusmanager.sysmgr.auth.mapper.SysMenuMapper;
import com.hcx.asclepiusmanager.sysmgr.auth.service.SysMenuService;
import com.hcx.asclepiusmanager.sysmgr.auth.service.SysUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author huangcaixia
 * @date 2022/3/30 11:04
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Resource
    SysMenuMapper sysMenuMapper;

    @Resource
    SysUserService sysUserService;

    @Override
    public Map<String, Object> getMenu(String role) {
        List<MenuVO> menuVOS=sysMenuMapper.getMenuVO(role);
        Map<String, Object> map = new HashMap<>(16);
        Map<String,Object> home = new HashMap<>(16);
        Map<String,Object> logo = new HashMap<>(16);
        TopMenu topMenu=new TopMenu();
        List<TopMenu> topMenus=new ArrayList<>();
        home.put("title","首页");
        home.put("href","page/welcome.html?t=1");


        logo.put("title","LoveChiWah");
        logo.put("image","images/logo.png");
        logo.put("href","");


        topMenu.setTitle("常规管理");
        topMenu.setIcon("fa fa-address-book");
        topMenu.setHref("");
        topMenu.setTarget("_self");
        topMenu.setChild(menuVOS);
        topMenus.add(topMenu);

        map.put("menuInfo",topMenus);
        map.put("logoInfo",logo);
        map.put("homeInfo",home);
        return map;
    }
}
