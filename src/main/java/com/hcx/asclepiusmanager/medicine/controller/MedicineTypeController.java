package com.hcx.asclepiusmanager.medicine.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hcx.asclepiusmanager.common.enums.ResultEnum;
import com.hcx.asclepiusmanager.common.utils.Result;
import com.hcx.asclepiusmanager.medicine.domain.Brand;
import com.hcx.asclepiusmanager.medicine.domain.MedicineType;
import com.hcx.asclepiusmanager.medicine.service.MedicineTypeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author huangcaixia
 * @date 2022/3/26 12:14
 */
@RestController
@RequestMapping("/medicineType")
public class MedicineTypeController {
    @Resource
    MedicineTypeService medicineTypeService;

    /**
     * 新增药品类型
     * @param medicineType
     * @return
     */
    @PostMapping("/save")
    public Result saveMedicineType(@RequestBody MedicineType medicineType) {
        Integer code = medicineTypeService.saveMedicineType(medicineType);
        if(0 != code) {
            return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(),"新增成功");
        }
        return new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg(),"该药品类型已存在");
    }

    @PostMapping("/findMedicineTypeWithPages/{pageNumber}/{pageSize}")
    public Result findMedicineTypeWithPages(@PathVariable("pageNumber") Integer pageNumber, @PathVariable("pageSize") Integer pageSize, @RequestBody MedicineType medicineType){

        PageHelper.startPage(pageNumber, pageSize);
        Result result=new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(new PageInfo(medicineTypeService.findMedicineTypeWithPages(medicineType)));
        return result;
    }

    @GetMapping("/findAllMedicineType")
    public Result findAllMedicineType(){
        Result result=new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(medicineTypeService.findAllMedicineType());
        return result;
    }
}
