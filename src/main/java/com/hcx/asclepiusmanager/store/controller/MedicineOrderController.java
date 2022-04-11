package com.hcx.asclepiusmanager.store.controller;

import com.hcx.asclepiusmanager.common.enums.ResultEnum;
import com.hcx.asclepiusmanager.common.utils.Result;
import com.hcx.asclepiusmanager.store.domain.MedicineOrderDTO;
import com.hcx.asclepiusmanager.store.domain.ShopCarDTO;
import com.hcx.asclepiusmanager.store.service.MedicineOrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author huangcaixia
 * @Description
 * @date 2022/4/6 21:18
 */
@RestController
@RequestMapping("/order")
public class MedicineOrderController {
    @Resource
    MedicineOrderService medicineOrderService;

    /**
     * TODO 测试
     * 提交订单
     * @param medicineOrderDTO
     * @return
     */
    @PostMapping("/save")
    public Result saveOrder(@RequestBody MedicineOrderDTO medicineOrderDTO) {
        Integer code = medicineOrderService.saveOrder(medicineOrderDTO);
        if(0 != code) {
            return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(),"提交订单成功");
        }
        if(code==-1){
            return new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg(),"库存不足");
        }
        return new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg(),"提交订单失败");
    }

    /**
     * 查询当前用户的订单
     * @return
     */
    @GetMapping("/findUserOrder")
    public Result findUserOrder(@RequestParam("status") Integer status) {
        Result result=new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(medicineOrderService.findUserOrder(status));
        return result;
    }

    /**
     * 查看订单详情
     * @return
     */
    @GetMapping("/getOrderInfoByOrderId")
    public Result getOrderInfo(@RequestParam("orderId") Integer orderId) {
        Result result=new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(medicineOrderService.getOrderInfo(orderId));
        return result;
    }
}
