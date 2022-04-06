package com.hcx.asclepiusmanager.sysmgr.wechat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hcx.asclepiusmanager.sysmgr.wechat.domain.SysWechat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author huangcaixia
 * @date 2022/3/26 19:53
 */
@Mapper
public interface SysWechatMapper extends BaseMapper<SysWechat> {

    @Select("select * from sys_wechat where open_id=#{openId}")
    SysWechat getWechatUserByOpenId(String openId);
}
