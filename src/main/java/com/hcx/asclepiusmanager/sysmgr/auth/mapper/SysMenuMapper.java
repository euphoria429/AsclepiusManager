package com.hcx.asclepiusmanager.sysmgr.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hcx.asclepiusmanager.sysmgr.auth.domain.MenuVO;
import com.hcx.asclepiusmanager.sysmgr.auth.domain.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author huangcaixia
 * @Description TODO
 * @date 2022/3/30 11:03
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    @Select("select title,href,icon,target from sys_menu where id in (select mid from sys_role_menu where rid=#{role})")
    List<MenuVO> getMenuVO(String role);
}
