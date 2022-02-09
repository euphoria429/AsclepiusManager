package com.hcx.asclepiusmanager.sysmgr.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hcx.asclepiusmanager.sysmgr.auth.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @ClassName SysUserMapper
 * @Description TODO
 * @Author hcx
 * @Date 2022/2/9 15:10
 * @Version 1.0
 **/
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("select * from sys_user where username=#{username}")
    SysUser findByUsername(String username);
}
