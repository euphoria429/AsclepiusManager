package com.hcx.asclepiusmanager.store.controller;

import com.hcx.asclepiusmanager.common.enums.ResultEnum;
import com.hcx.asclepiusmanager.common.utils.Result;
import com.hcx.asclepiusmanager.store.domain.ShopCarDTO;
import com.hcx.asclepiusmanager.store.domain.ShopCarUpdateDTO;
import com.hcx.asclepiusmanager.store.service.ShopCarService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author huangcaixia
 * @date 2022/4/2 17:43
 */
@RestController
@RequestMapping("/shopCar")
public class ShopCarController {
    @Resource
    ShopCarService shopCarService;

    /**
     * TODO 测试
     * 新增购物车，新增的数量默认为1
     * @param shopCarDTO
     * @return
     */
    @PostMapping("/save")
    public Result saveShopCar(@RequestBody ShopCarDTO shopCarDTO) {
        Integer code = shopCarService.saveShopCar(shopCarDTO);
        if(0 != code) {
            return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(),"新增购物车成功");
        }
        return new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg(),"新增购物车失败");
    }

    /**
     * TODO 测试
     * 查询当前用户的购物车列表
     * @return
     */
    @GetMapping("/findUserShopCar")
    public Result saveShopCar() {
        Result result=new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(shopCarService.findUserShopCar());
        return result;
    }

    /**
     * TODO 测试
     * 修改购物车药品数量，只能以1为单位修改
     * @param shopCarUpdateDTO
     * @return
     */
    @PostMapping("/update")
    public Result updateShopCar(@RequestBody ShopCarUpdateDTO shopCarUpdateDTO) {
        Integer code = shopCarService.updateShopCar(shopCarUpdateDTO);
        if(0 != code) {
            return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(),"修改成功");
        }
        return new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg(),"修改失败");
    }


    /**
     * TODO 测试
     * 删除购物车记录
     * @param shopCarId
     * @return
     */
    @PostMapping("/delete")
    public Result deleteShopCar(Integer shopCarId) {
        Integer code = shopCarService.deleteShopCar(shopCarId);
        if(0 != code) {
            return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(),"删除成功");
        }
        return new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg(),"删除失败");
    }

}
