package com.hcx.asclepiusmanager.store.controller;

import com.github.pagehelper.PageInfo;
import com.hcx.asclepiusmanager.common.enums.ResultEnum;
import com.hcx.asclepiusmanager.common.utils.Result;
import com.hcx.asclepiusmanager.store.domain.Address;
import com.hcx.asclepiusmanager.store.service.AddressService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author huangcaixia
 * @Description TODO
 * @date 2022/3/28 21:35
 */
@RestController
@RequestMapping("/address")
public class AddressController {

    @Resource
    AddressService addressService;

    @PostMapping("/save")
    public Result save(@RequestBody Address address) {
        Integer code = addressService.saveAddress(address);
        if (0 != code) {
            return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), "新增地址成功");
        }
        return new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg(), "新增地址失败");
    }

    @PostMapping("/update")
    public Result update(@RequestBody Address address) {
        Integer code = addressService.updateAddress(address);
        if (0 != code) {
            return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), "修改地址成功");
        }
        return new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg(), "修改地址失败");
    }

    @GetMapping("/findAllAddress")
    public Result findAllAddress(){
        Result result=new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(addressService.findAllAddress());
        return result;
    }


    @PostMapping("/delete")
    public Result delete(Integer addressId){
        Integer code = addressService.deleteAddress(addressId);
        if (0 != code) {
            return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), "删除地址成功");
        }
        return new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg(), "删除地址失败");
    }

}
