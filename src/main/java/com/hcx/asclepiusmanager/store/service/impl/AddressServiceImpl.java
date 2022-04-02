package com.hcx.asclepiusmanager.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hcx.asclepiusmanager.store.domain.Address;
import com.hcx.asclepiusmanager.store.mapper.AddressMapper;
import com.hcx.asclepiusmanager.store.service.AddressService;
import com.hcx.asclepiusmanager.sysmgr.wechat.domain.SysWechat;
import com.hcx.asclepiusmanager.sysmgr.wechat.service.SysWechatService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author huangcaixia
 * @Description TODO
 * @date 2022/3/28 21:35
 */
@Service
public class AddressServiceImpl implements AddressService {
    @Resource
    AddressMapper addressMapper;

    @Resource
    SysWechatService sysWechatService;

    @Override
public Integer saveAddress(Address address) {
    address.setUserOpenId(sysWechatService.getUserInfo().getOpenId());
    return addressMapper.insert(address);
}

    @Override
    public Integer updateAddress(Address address) {
        return addressMapper.updateById(address);
    }

    @Override
    public List<Address> findAllAddress() {
        //获取openid
        String openId=sysWechatService.getUserInfo().getOpenId();
        //根据openid查询
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        wrapper.eq("user_open_id", openId);
        return addressMapper.selectList(wrapper);
    }

    @Override
    public Integer deleteAddress(Integer addressId) {
        if(addressId==null||addressMapper.selectById(addressId)==null){
            return 0;
        }
        return addressMapper.deleteById(addressId);
    }
}
