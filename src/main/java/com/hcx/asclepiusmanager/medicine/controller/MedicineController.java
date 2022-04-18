package com.hcx.asclepiusmanager.medicine.controller;

import com.hcx.asclepiusmanager.common.enums.ResultEnum;
import com.hcx.asclepiusmanager.common.utils.Result;
import com.hcx.asclepiusmanager.medicine.domain.*;
import com.hcx.asclepiusmanager.medicine.service.MedicineService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author huangcaixia
 * @date 2022/3/26 12:03
 */
@RestController
@RequestMapping("/medicine")
public class MedicineController {
    @Resource
    MedicineService medicineService;

    /** 后台查询药品接口，为了适应前端layui，返回带count
     * @param medicineRequest
     * @return
     */
    @GetMapping("/findMedicineWithPages")
    public Result findMedicineWithPages(MedicineRequest medicineRequest){
        Result result=new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        List<MedicineVO> medicines=medicineService.findMedicineWithPages(medicineRequest);
        result.setCount(medicines.size());
        result.setData(medicines);
        return result;
    }

    /**
     * TODO 添加了商品状态 待测试
     * 小程序查询药品接口，多一个返回参数销量（number）,且商品状态为在售
     * @param medicineRequest
     * @return
     */
    @GetMapping("/findMedicineWithNumber")
    public Result findMedicineWithNumber(MedicineRequest medicineRequest){
        Result result=new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        List<MedicineNumberVO> medicines=medicineService.findMedicineWithNumber(medicineRequest);
        result.setData(medicines);
        return result;
    }

    /**
     * 小程序根据药品id查询药品接口
     * @param medicineId
     * @return
     */
    @GetMapping("/findMedicineById")
    public Result findMedicineById(@RequestParam("medicineId")Integer medicineId){
        Result result=new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        if(medicineId==null){
            result.setData(null);
            return result;
        }
        MedicineRequest medicineRequest=new MedicineRequest();
        medicineRequest.setId(medicineId);
        List<MedicineNumberVO> medicines=medicineService.findMedicineWithNumber(medicineRequest);
        result.setData(medicines.get(0));
        return result;
    }

    @PostMapping("/save")
    public Result saveMedicine(MedicineDTO medicineDTO){
        Integer code = medicineService.saveMedicine(medicineDTO);
        if(0 != code) {
            return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(),"新增成功");
        }
        return new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg(),"新增药品失败");
    }

    /**
     * 手动修改库存
     * @param Medicine
     * @return
     */
    @PostMapping("/updateStock")
    public Result updateStock(Medicine Medicine){
        Integer code = medicineService.updateStock(Medicine);
        if(0 != code) {
            return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(),"更新库存成功");
        }
        return new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg(),"更新库存失败");
    }

    /**
     * 入库
     * @param Medicine
     * @return
     */
    @PostMapping("/addStock")
    public Result addStock(Medicine Medicine){
        Integer code = medicineService.addStock(Medicine);
        if(0 != code) {
            return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(),"更新库存成功");
        }
        return new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg(),"更新库存失败");
    }

    @PostMapping("/updateMedicine")
    public Result updateMedicine(Medicine Medicine){
        Integer code = medicineService.updateMedicine(Medicine);
        if(0 != code) {
            return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(),"更新药品信息成功");
        }
        return new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg(),"更新药品信息失败");
    }

    @PostMapping("/changeSoldStatus")
    public Result changeSoldStatus(Medicine Medicine){
        Integer code = medicineService.changeSoldStatus(Medicine);
        if(0 != code) {
            return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(),"操作成功");
        }
        return new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg(),"操作失败");
    }

    /**
     * 网页后端：统计用户、药品、订单
     * @return
     */
    @GetMapping("/findCountInfo")
    public Result findCountInfo(){
        Result result=new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        Map<String, Integer> info=medicineService.findCountInfo();
        result.setData(info);
        return result;
    }

}
