package com.hcx.asclepiusmanager.medicine.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hcx.asclepiusmanager.medicine.domain.Brand;
import com.hcx.asclepiusmanager.medicine.mapper.BrandMapper;
import com.hcx.asclepiusmanager.medicine.service.BrandService;
import com.hcx.asclepiusmanager.medicine.service.MedicineService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author huangcaixia
 * @date 2022/3/26 12:09
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Resource
    BrandMapper brandMapper;

    @Override
    public Integer saveBrand(Brand brand) {
        if(StringUtils.isNotBlank(brand.getBrandName())) {
            List<Brand> brandList = brandMapper.findByBrandName(brand.getBrandName());
            if(!brandList.isEmpty()) {
                return 0;
            }
        } else {
            return 0;
        }
        return brandMapper.insert(brand);
    }

    @Override
    public List findBrandWithPages(Brand brand) {
        List<Brand> brandList = brandMapper.findBrandByOption(brand);
        return brandList;
    }

    @Override
    public List findAllBrand() {
        Brand brand=new Brand();
        return brandMapper.findBrandByOption(brand);
    }

    @Override
    public Brand findBrandById(Integer brandId) {
        return brandMapper.selectById(brandId);
    }
}
