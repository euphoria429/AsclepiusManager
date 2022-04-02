package com.hcx.asclepiusmanager.medicine.controller;

import com.hcx.asclepiusmanager.common.enums.ResultEnum;
import com.hcx.asclepiusmanager.common.utils.Result;
import com.hcx.asclepiusmanager.medicine.domain.Medicine;
import com.hcx.asclepiusmanager.medicine.domain.MedicineDTO;
import com.hcx.asclepiusmanager.medicine.domain.MedicineRequest;
import com.hcx.asclepiusmanager.medicine.domain.MedicineVO;
import com.hcx.asclepiusmanager.medicine.service.MedicineService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author huangcaixia
 * @Description TODO
 * @date 2022/3/26 12:03
 */
@RestController
@RequestMapping("/medicine")
public class MedicineController {
    @Resource
    MedicineService medicineService;

    /**
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


}
