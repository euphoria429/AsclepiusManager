package com.hcx.asclepiusmanager.medicine.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hcx.asclepiusmanager.common.enums.ResultEnum;
import com.hcx.asclepiusmanager.common.utils.Result;
import com.hcx.asclepiusmanager.medicine.domain.Brand;
import com.hcx.asclepiusmanager.medicine.service.BrandService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author huangcaixia
 * @date 2022/3/26 12:10
 */
@RestController
@RequestMapping("/brand")
public class BrandController {
    @Resource
    BrandService brandService;

    /**
     * 新增品牌
     * @param brand
     * @return
     */
    @PostMapping("/save")
    public Result saveBrand( @RequestBody Brand brand) {
        Integer code = brandService.saveBrand(brand);
        if(0 != code) {
            return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(),"新增成功");
        }
        return new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg(),"该品牌已存在");
    }

    @PostMapping("/findBrandWithPages/{pageNumber}/{pageSize}")
    public Result findBrandWithPages(@PathVariable("pageNumber") Integer pageNumber, @PathVariable("pageSize") Integer pageSize, @RequestBody Brand brand){
        PageHelper.startPage(pageNumber, pageSize);
        Result result=new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(new PageInfo(brandService.findBrandWithPages(brand)));
        return result;
    }

    @GetMapping("/findAllBrand")
    public Result findAllBrand(){
        Result result=new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(brandService.findAllBrand());
        return result;
    }
}
